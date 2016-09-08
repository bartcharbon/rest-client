package org.molgenis;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.apache.camel.spring.javaconfig.Main;
import org.molgenis.messages.LoginRequest;
import org.molgenis.messages.LoginResponse;
import org.molgenis.messages.QueryResponse;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

import static org.apache.camel.Exchange.*;
import static org.apache.camel.component.http4.HttpMethods.GET;
import static org.apache.camel.component.http4.HttpMethods.POST;
import static org.apache.camel.model.dataformat.JsonLibrary.Jackson;
import static org.apache.camel.processor.idempotent.MemoryIdempotentRepository.memoryIdempotentRepository;
import static org.apache.commons.csv.CSVFormat.EXCEL;

@Component
public class MyRouteBuilder extends RouteBuilder {

    public static final String MOLGENIS_SERVER = "MolgenisServer";
    public static final String ENTITY_NAMES = "EntityNames";
    private static final String ENTITY_NAME = "EntityName";

    /**
     * Allow this route to be run as an application
     */
    public static void main(String[] args) throws Exception {
        // delete results dir
        delete(new File("target/data"));
        Main main = new Main();
        main.setConfigClass(RouteConfiguration.class);
        main.run(args);
    }

    static void delete(File f) throws IOException {
        if (f.isDirectory()) {
            for (File c : f.listFiles())
                delete(c);
        }
        f.delete();
    }

    public void configure() throws IOException {
        CsvDataFormat csvDataFormat = new CsvDataFormat(EXCEL);
        csvDataFormat.setAllowMissingColumnNames(true);

        // Reads and parses config files from config directory
        from("file://src/main/resources/config?noop=true")
                .log("Found config file ${header.CamelFileName}")
                .unmarshal()
                .json(Jackson)
                .setHeader(MOLGENIS_SERVER, simple("body[server]"))
                .setHeader(ENTITY_NAMES, simple("body[entities]"))
                .marshal()
                // trim config from login request
                .json(Jackson)
                .unmarshal()
                .json(Jackson, LoginRequest.class)
                .to("direct:login");

        // message body contains LoginRequest, header MOLGENIS_SERVER contains the server
        from("direct:login")
                .log("Login...")
                .marshal()
                .json(Jackson)
                .setHeader(HTTP_METHOD, constant(POST))
                .setHeader(CONTENT_TYPE, constant("application/json"))
                .setHeader(HTTP_URI, simple("https://${header.MolgenisServer}/api/v1/login"))
                .to("https4://loginRequest")
                .unmarshal()
                .json(Jackson, LoginResponse.class)
                .log("Logged in. ${body}")
                .setHeader("x-molgenis-token", simple("${body.token}"))
                .to("direct:requestEntities");

        // body contains List<String> with entity names, header MOLGENIS_SERVER the server name
        from("direct:requestEntities")
                .log("Request entities...")
                .split(simple("${header.EntityNames}"))
                .setHeader(HTTP_URI, simple("https://${header.MolgenisServer}/api/v2/${body}"))
                .setHeader(ENTITY_NAME, simple("${body}"))
                .to("direct:getPage");

        // Retrieves a page of entities, from the URL in CamelHttpUri
        from("direct:getPage")
                .log("Requesting entity ${header.EntityName} from ${header.CamelHttpUri}...")
                .setHeader(HTTP_METHOD, constant(GET))
                .to("https4://entityRequest")
                .unmarshal()
                .json(Jackson, QueryResponse.class)
                .multicast()
                .to("direct:writeUniqueHeader")
                .to("direct:writeResponseToCsv")
                .to("direct:getNextPage");

        from("direct:writeResponseToCsv")
                .log("Parse data rows for entity ${header.EntityName}")
                .bean(new WriteData())
                .to("direct:writeToCsv");

        from("direct:writeUniqueHeader")
                .idempotentConsumer(header(ENTITY_NAME),
                        memoryIdempotentRepository(200))
                .log("Create header row for entity ${header.EntityName}")
                .bean(new WriteHeaders())
                .to("direct:writeToCsv");

        // Retrieves the next page of entities, if the QueryResponse in the body has a nextHref
        from("direct:getNextPage")
                .filter(simple("${body.nextHref} != null"))
                .setHeader(HTTP_URI, simple("body.nextHref"))
                .setBody(constant(""))
                .to("direct:getPage");

        // Appends QueryResponse to csv file
        from("direct:writeToCsv")
                .marshal(csvDataFormat)
                .setHeader(FILE_NAME, simple("${header.MolgenisServer}/${header.EntityName}"))
                .to("file://target/data?fileExist=Append");

    }
}
