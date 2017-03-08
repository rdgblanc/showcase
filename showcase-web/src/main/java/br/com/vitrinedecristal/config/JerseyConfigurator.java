package br.com.vitrinedecristal.config;

import io.swagger.jaxrs.config.BeanConfig;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.apache.log4j.Logger;

@ApplicationPath("/api")
public class JerseyConfigurator extends Application {

	private static final Logger logger = Logger.getLogger(JerseyConfigurator.class);

	public JerseyConfigurator() {
		BeanConfig beanConfig = new BeanConfig();
		beanConfig.setVersion("1.0.0");
		beanConfig.setBasePath("/vitrinedecristal/api");
		beanConfig.setResourcePackage("br.com.vitrinedecristal");
		beanConfig.setScan(true);

		logger.debug("Jersey config applied");
	}

}