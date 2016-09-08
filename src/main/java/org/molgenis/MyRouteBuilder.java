package org.molgenis;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.apache.camel.model.language.JsonPathExpression;
import org.apache.camel.spring.javaconfig.Main;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

import static org.apache.camel.Exchange.*;
import static org.apache.camel.component.http4.HttpMethods.GET;
import static org.apache.camel.component.http4.HttpMethods.POST;
import static org.apache.camel.model.dataformat.JsonLibrary.Gson;
import static org.apache.commons.csv.CSVFormat.EXCEL;

@Component
public class MyRouteBuilder extends RouteBuilder
{
	/**
	 * Allow this route to be run as an application
	 */
	public static void main(String[] args) throws Exception
	{
		Main main = new Main();
		main.setConfigClass(RouteConfiguration.class);
		main.run(args);
	}

	public void configure() throws IOException
	{
		CsvDataFormat csvDataFormat = new CsvDataFormat(EXCEL);
		EntityMapToCsvRow entityMapToCsvRow = new EntityMapToCsvRow();

		// Read config and start
		from("file://src/main/resources/?noop=true")
				.unmarshal()
				.json(Gson, Map.class)
				.setHeader("server", simple("body[server]"))
				.setHeader("entities", simple("body[entities]"))
				.marshal()
				.json(Gson, Map.class)
				.to("direct:login");

		// Use properties to login
		from("direct:login")
				.log("Login!")
				.setHeader(HTTP_METHOD, constant(POST)).setHeader(CONTENT_TYPE, constant("application/json"))
				.setHeader(HTTP_URI, simple("https://${header.server}/api/v1/login"))
				.to("https4://loginRequest")
				.setHeader("x-molgenis-token", new JsonPathExpression("$.token"))
				.to("direct:requestEntities");

		// Split the entities in the header and retrieve data per entity
		from("direct:requestEntities")
				.log("Request entities!")
				.split(simple("${header.entities}"))
				.to("direct:getPage");

		// Parse metadata and data
		from("direct:getPage")
				.setHeader(HTTP_METHOD, constant(GET))
				.log("${headers}")
				.setHeader(HTTP_URI, simple("https://${header.server}/api/v2/${body}"))
				.to("https4://entityRequest")
				.unmarshal().json(Gson, Map.class)
				.log("${body}")
				.setHeader("attributes", simple("${body[meta][attributes]}"))
				.setBody(simple("${body[items]}"))
				.bean(entityMapToCsvRow)
				.log("${body}")
				.marshal(csvDataFormat)
				.to("file://target/data");
	}
}
