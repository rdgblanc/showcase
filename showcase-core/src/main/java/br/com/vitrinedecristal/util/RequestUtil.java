package br.com.vitrinedecristal.util;

public class RequestUtil {

	private static RequestUtil instance;

	private String requestIp;

	public RequestUtil() {
	}

	public static RequestUtil getInstance() {
		if (instance == null) {
			instance = new RequestUtil();
		}
		return instance;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	public String getClientIp() {
		return this.requestIp;
	}

}