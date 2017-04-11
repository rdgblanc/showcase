package br.com.vitrinedecristal.log;

import org.apache.log4j.Logger;

import br.com.vitrinedecristal.request.RequestInfo;
import br.com.vitrinedecristal.request.RequestUtilsFactory;

/**
 * Implementação do TID (Tracking ID) para Log.<br>
 * Irá utilizar {@link RequestInfo} para informações eventuais da requisição/ negócio.<br>
 * O TID tem como função auxiliar no controle de requisições, principalmente em sistemas clusterizados.<br>
 * Esta identificação única de cada request facilita, por exemplo, a compreensão do conteúdo de log (log4j) gerado na requisição.
 */
public class TrackingLogger extends Logger {

	private static TrackingLoggerFactory trackingLoggerFactory = new TrackingLoggerFactory();

	@SuppressWarnings("rawtypes")
	public static Logger getLogger(Class clazz) {
		return Logger.getLogger(clazz.getName(), trackingLoggerFactory);
	}

	protected TrackingLogger(String name) {
		super(name);
	}

	/**
	 * Log debug customizado. Todos os parâmetros informados serão inclusos na mensagem. Exemplo: "Dados do contrato. Parâmetros: [parametro1] [parametro2] [parametro3]"
	 * 
	 * @param message Mensagem de log
	 * @param params Parametros
	 */
	public void debug(String message, Object... params) {
		StringBuilder builder = new StringBuilder();
		builder.append(getRequestInfoTid()).append(message).append(". Parâmetros: ");
		for (Object param : params) {
			builder.append(" [ ").append(param).append(" ] ");
		}
		super.debug(builder.toString());
	}

	/**
	 * Log info customizado (sem TID)
	 * 
	 * @param message Mensagem de log
	 */
	public void infoWithoutTid(Object message) {
		super.info(message);
	}

	/**
	 * Log Info customizado. Parâmetro informado será incluso na mensagem. Exemplo: "Consulta por nome realizada com sucesso. Parâmetro: [parametro]"
	 * 
	 * @param message Mensagem de log
	 * @param param Parâmetro relevante
	 */
	public void info(String message, Object param) {
		StringBuilder builder = new StringBuilder();
		builder.append(getRequestInfoTid()).append(message).append(". Parâmetro: [ ").append(param).append(" ]");
		super.info(builder.toString());
	}

	@Override
	public void debug(Object message) {
		super.debug(new StringBuilder().append(getRequestInfoTid()).append(message).toString());
	}

	@Override
	public void info(Object message) {
		super.info(new StringBuilder().append(getRequestInfoTid()).append(message).toString());
	}

	@Override
	public void warn(Object message) {
		super.warn(new StringBuilder().append(getRequestInfoTid()).append(message).toString());
	}

	@Override
	public void warn(Object message, Throwable t) {
		super.warn(new StringBuilder().append(getRequestInfoTid()).append(message).toString(), t);
	}

	@Override
	public void error(Object message) {
		super.error(new StringBuilder().append(getRequestInfoTid()).append(message).toString());
	}

	@Override
	public void error(Object message, Throwable t) {
		super.error(new StringBuilder().append(getRequestInfoTid()).append(message).toString(), t);
	}

	private String getRequestInfoTid() {
		RequestInfo requestInfo = RequestUtilsFactory.createRequestUtils().getRequestInfo();
		if (requestInfo == null) {
			return "";
		}
		return RequestUtilsFactory.createRequestUtils().getRequestInfo().getTid();
	}
}