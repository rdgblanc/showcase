package br.com.vitrinedecristal.request;

/**
 * Interface de contrato para inserir e remover um RequestInfo da ThreadLocal
 */
public interface IRequestInfo {

	/**
	 * Recupera o RequestInfo na ThreadLocal
	 * 
	 * @return requestInfo
	 */
	RequestInfo getRequestInfo();

	/**
	 * Coloca o request no contexto
	 *
	 * @param requestInfo
	 */
	public void storeRequest(RequestInfo requestInfo);

	/**
	 * Remove variáveis do contexto
	 */
	public void release();

}