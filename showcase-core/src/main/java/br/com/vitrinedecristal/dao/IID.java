package br.com.vitrinedecristal.dao;

import java.io.Serializable;

/**
 * Descreve o contrato de uma entidade que possui uma chave (ID)
 */
public interface IID<T extends Serializable> {

	/**
	 * Retorna a chave primária da entidade.
	 * 
	 * @return a chave primária da entidade.
	 */
	public T getId();

	public void setId(T id);

}
