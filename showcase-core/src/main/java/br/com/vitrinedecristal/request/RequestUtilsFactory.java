package br.com.vitrinedecristal.request;

public class RequestUtilsFactory {

	private static RequestUtils requestUtils;

	/**
	 * Cria o {@link RequestUtils}
	 * 
	 * @return
	 */
	public static RequestUtils createRequestUtils() {
		if (requestUtils == null) {
			requestUtils = new RequestUtils();
		}
		return requestUtils;
	}

}