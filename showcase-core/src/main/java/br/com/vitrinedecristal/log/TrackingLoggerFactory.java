package br.com.vitrinedecristal.log;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

/**
 * Implementação de LoggerFactory. Obtêm uma instância de {@link TrackingLogger}
 */
public class TrackingLoggerFactory implements LoggerFactory {

	@Override
	public Logger makeNewLoggerInstance(String name) {
		return new TrackingLogger(name);
	}

}
