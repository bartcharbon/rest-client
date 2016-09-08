package org.molgenis;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.apache.camel.model.language.JsonPathExpression;
import org.apache.camel.spring.javaconfig.Main;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.apache.camel.Exchange.*;
import static org.apache.camel.component.http4.HttpMethods.GET;
import static org.apache.camel.component.http4.HttpMethods.POST;
import static org.apache.camel.model.dataformat.JsonLibrary.Gson;
import static org.apache.commons.csv.CSVFormat.EXCEL;

@Component
public class MyRouteBuilder extends RouteBuilder {

    public static final String MOLGENIS_SERVER = "MolgenisServer";
    public static final String ENTITY_NAMES = "EntityNames";
    private static final String ENTITY_NAME = "EntityName";
    private Map<String, Object> blah = new HashMap<>();

    /**
     * Allow this route to be run as an application
     */
    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.setConfigClass(RouteConfiguration.class);
        main.run(args);
    }

    public void configure() throws IOException {
        CsvDataFormat csvDataFormat = new CsvDataFormat(EXCEL);
        EntityMapToCsvRow entityMapToCsvRow = new EntityMapToCsvRow();

        // Read config and start
        from("file://src/main/resources/config?noop=true")
                .unmarshal()
                .json(Gson)
                .setHeader(MOLGENIS_SERVER, simple("body[server]"))
                .setHeader(ENTITY_NAMES, simple("body[entities]"))
                .marshal()
                .json(Gson)
                .to("direct:login");

        // Use properties to login
        from("direct:login")
                .log("Login!")
                .setHeader(HTTP_METHOD, constant(POST))
                .setHeader(CONTENT_TYPE, constant("application/json"))
                .setHeader(HTTP_URI, simple("http://${header.MolgenisServer}/api/v1/login"))
                .to("http4://loginRequest")
                .setHeader("x-molgenis-token", new JsonPathExpression("$.token"))
                .to("direct:requestEntities");

        // Split the entities in the header and retrieve data per entity
        from("direct:requestEntities")
                .log("Request entities!")
                .split(simple("${header.EntityNames}"))
                .setHeader(HTTP_URI, simple("http://${header.MolgenisServer}/api/v2/${body}"))
                .setHeader(ENTITY_NAME, simple("${body}"))
                .to("direct:getPage");

        // Retrieves a page of entities, from the URL
        from("direct:getPage")
                .setHeader(HTTP_METHOD, constant(GET))
                .log("Requesting entity from ${header.CamelHttpUri}")
                .to("http4://entityRequest")
                .unmarshal()
                .json(Gson)
                .setHeader("attributes", simple("body[meta][attributes]"))
                .setBody(simple("body[items]"))
                .bean(entityMapToCsvRow)
                .marshal(csvDataFormat)
                .setHeader(FILE_NAME, simple("header.EntityName"))
                .to("file://target/data");
    }
}
