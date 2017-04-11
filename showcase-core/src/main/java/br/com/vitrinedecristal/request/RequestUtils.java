package br.com.vitrinedecristal.request;

public class RequestUtils implements IRequestInfo {

	private ThreadLocal<RequestInfo> localPrincipal = new ThreadLocal<RequestInfo>();

	/**
	 * Recupera o RequestInfo na ThreadLocal
	 * 
	 * @return
	 */
	@Override
	public RequestInfo getRequestInfo() {
		return localPrincipal.get();
	}

	@Override
	public void storeRequest(RequestInfo requestInfo) {
		localPrincipal.set(requestInfo);

	}

	@Override
	public void release() {
		localPrincipal.remove();
	}

}