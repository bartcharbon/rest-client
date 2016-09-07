package org.molgenis;

import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.molgenis")
public class RouteConfiguration extends CamelConfiguration
{

}
