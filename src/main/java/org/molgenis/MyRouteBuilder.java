package org.molgenis;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.language.JsonPathExpression;
import org.apache.camel.spring.javaconfig.Main;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.apache.camel.Exchange.CONTENT_TYPE;
import static org.apache.camel.Exchange.HTTP_METHOD;
import static org.apache.camel.component.http4.HttpMethods.GET;
import static org.apache.camel.component.http4.HttpMethods.POST;
import static org.apache.commons.csv.CSVFormat.EXCEL;

@Component
public class MyRouteBuilder extends RouteBuilder {
    /**
     * Allow this route to be run as an application
     */
    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.setConfigClass(RouteConfiguration.class);
        main.run(args);
    }

    public void configure() {
        CsvDataFormat csvDataFormat = new CsvDataFormat(EXCEL);

        EntityMapToCsvRow entityMapToCsvRow = new EntityMapToCsvRow();

        // set up a listener on the file component
        from("file://src/data?noop=true")
                .setHeader(HTTP_METHOD, constant(POST))
                .setHeader(CONTENT_TYPE, constant("application/json"))
                .to("https4://molgenis01.gcc.rug.nl/api/v1/login")
                .setHeader("x-molgenis-token", new JsonPathExpression("$.token"))
                .setBody(constant("https://molgenis01.gcc.rug.nl/api/v2/org_molgenis_test_TypeTest"))
                .log("$simple{header.x-molgenis-token}")
                .to("direct:getPage");

        from("direct:getPage")
                .setHeader(HTTP_METHOD, constant(GET))
                .to("https4://molgenis01.gcc.rug.nl/api/v2/org_molgenis_test_TypeTest") //TODO: wat nu?!?
                .multicast()
                .choice()
                .unmarshal()
                .json(JsonLibrary.Gson, Map.class)
                .setHeader("attributes", simple("${body[meta][attributes]}"))
                .setBody(simple("${body[items]}"))
                .bean(entityMapToCsvRow)
                .log("${body}")
                .marshal(csvDataFormat)
                .to("file://target/data");
    }
}
