package org.molgenis;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.language.JsonPathExpression;
import org.apache.camel.spring.javaconfig.Main;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static org.apache.camel.Exchange.CONTENT_TYPE;
import static org.apache.camel.Exchange.HTTP_METHOD;
import static org.apache.camel.component.http4.HttpMethods.GET;
import static org.apache.camel.component.http4.HttpMethods.POST;
import static org.apache.commons.csv.CSVFormat.EXCEL;

@Component
public class MyRouteBuilder extends RouteBuilder
{
	// Commandline variables
	private static final String CONFIG = "config";
	private static final String OUTPUT = "output";

	// Configuration file options
	private static String SERVER = "server";
	private static String USERNAME = "username";
	private static String PASSWORD = "password";
	private static String ENTITIES = "entities";

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
		Properties properties = new Properties();
		properties.load(new FileInputStream(
				new File("/Users/mdehaan/git/rest-client/src/main/resources/migration-config.properties")));

		// Set all configurations
		Set<String> entities = new HashSet<>();
		Collections.addAll(entities, properties.getProperty(ENTITIES).split(","));
		String server = properties.getProperty(SERVER);
		String username = properties.getProperty(USERNAME);
		String password = properties.getProperty(PASSWORD);

		CsvDataFormat csvDataFormat = new CsvDataFormat(EXCEL);
		EntityMapToCsvRow entityMapToCsvRow = new EntityMapToCsvRow();

		// for(String entity : entities){ // do stuff}

		String entity = "org_molgenis_test_TypeTest";
		String login = "{user:" + username + ",password:" + password + "}";

		// set up a listener on the file component
		from(login).setHeader(HTTP_METHOD, constant(POST))
				.setHeader(CONTENT_TYPE, constant("application/json")).to("https4://" + server + "/api/v1/login")
				.setHeader("x-molgenis-token", new JsonPathExpression("$.token"))
				.setBody(constant("https://" + server + "/api/v2/" + entity)).log("$simple{header.x-molgenis-token}")
				.to("direct:getPage");

		from("direct:getPage").setHeader(HTTP_METHOD, constant(GET))
				.to("https4://" + server + "/api/v2/" + entity) //TODO: wat nu?!?
				.unmarshal().json(JsonLibrary.Gson, Map.class)
				.setHeader("attributes", simple("${body[meta][attributes]}")).setBody(simple("${body[items]}"))
				.bean(entityMapToCsvRow).log("${body}").marshal(csvDataFormat).to("file://target/data");

	}
}
