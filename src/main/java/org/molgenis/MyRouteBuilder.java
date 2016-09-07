package org.molgenis;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.language.JsonPathExpression;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static org.apache.camel.Exchange.CONTENT_TYPE;
import static org.apache.camel.Exchange.HTTP_METHOD;
import static org.apache.camel.component.http4.HttpMethods.GET;
import static org.apache.camel.component.http4.HttpMethods.POST;
import static org.apache.commons.csv.CSVFormat.EXCEL;

@Component
public class MyRouteBuilder extends RouteBuilder
{

	private String server;
	private String username;
	private String password;

	private Set<String> entities;

	private File outputDirectory;

	public MyRouteBuilder(String server, Set<String> entities, String username, String password, File outputDirectory)
	{
		this.server = requireNonNull(server);
		this.username = requireNonNull(username);
		this.password = requireNonNull(password);
		this.entities = requireNonNull(entities);
		this.outputDirectory = requireNonNull(outputDirectory);
	}

	public void configure()
	{
		String login = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";

		CsvDataFormat csvDataFormat = new CsvDataFormat(EXCEL);
		EntityMapToCsvRow entityMapToCsvRow = new EntityMapToCsvRow();

		for(String entity : entities)
		{
			// set up a listener on the file component
			from(login)
					.setHeader(HTTP_METHOD, constant(POST))
					.setHeader(CONTENT_TYPE, constant("application/json"))
					.to("https4://" + server + "/api/v1/login")
					.setHeader("x-molgenis-token", new JsonPathExpression("$.token"))
					.setBody(constant("https://" + server + "/api/v2/" + entity))
					.log("$simple{header.x-molgenis-token}")
					.to("direct:getPage");

			from("direct:getPage")
					.setHeader(HTTP_METHOD, constant(GET))
					.to("https4://" + server + "/api/v2/" + entity) //TODO: wat nu?!?
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
}
