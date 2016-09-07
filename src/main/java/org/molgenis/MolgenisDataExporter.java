package org.molgenis;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.apache.camel.spring.javaconfig.Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Properties;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static java.lang.System.exit;

public class MolgenisDataExporter
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
		MyRouteBuilder routeBuilder = createRouteBuilder(args);

		routeBuilder.configure();
		main.run(args);
	}

	private static MyRouteBuilder createRouteBuilder(String[] args) throws IOException
	{
		// Configure command line options
		OptionParser parser = createOptionParser();
		OptionSet options = parser.parse(args);
		if (!options.has(CONFIG) || !options.has(OUTPUT))
		{
			System.out.println("You are missing one or more command line options, see below for more details");
			parser.printHelpOn(System.out);
			exit(1);
		}

		Properties properties = new Properties();
		properties.load(new FileInputStream((File) options.valueOf(CONFIG)));

		// Set all configurations
		Set<String> entities = newHashSet();
		Collections.addAll(entities, properties.getProperty(ENTITIES).split(","));
		String url = properties.getProperty(SERVER);
		String username = properties.getProperty(USERNAME);
		String password = properties.getProperty(PASSWORD);
		File outputDirectory = (File) options.valueOf(OUTPUT);

		return new MyRouteBuilder(url, entities, username, password, outputDirectory);
	}

	private static OptionParser createOptionParser()
	{
		OptionParser parser = new OptionParser();

		parser.acceptsAll(newArrayList("c", CONFIG), "Configuration file").withRequiredArg().ofType(File.class);
		parser.acceptsAll(newArrayList("o", OUTPUT), "Output Directory").withRequiredArg().ofType(File.class);

		return parser;
	}
}
