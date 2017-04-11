package br.com.vitrinedecristal.service.base;

import java.io.Serializable;
import java.util.List;

import br.com.vitrinedecristal.dao.IBaseDAO;
import br.com.vitrinedecristal.dao.IID;

/**
 * Estabelece os contratos básicos de negócio de uma entidade.
 * 
 * @param <B> tipo da chave primária da entidade.
 * @param <T> tipo da entidade.
 * @param <D> tipo do DAO.
 */
public interface IBaseService<B extends Serializable, T extends IID<B>, D extends IBaseDAO<B, T>> {

	/**
	 * Persiste a entidade na base.
	 * 
	 * @param t a entidade a ser persistida.
	 * @return a entidade persistida.
	 * @see IBaseDAO#save(IUID)
	 */
	public T save(T t);

	/**
	 * Exclui a entidade da base.
	 * 
	 * @param t a entidade a ser excluída.
	 * @see IBaseDAO#remove(IUID)
	 */
	public void remove(T t);

	/**
	 * Pesquisa pela entidade por chave primária.
	 * 
	 * @param b a chave primária da entidade a ser pesquisada.
	 * @return a entidade pesquisada ou <code>null</code> caso a entidade não exista.
	 * @see IBaseDAO#findByPrimaryKey(Serializable)
	 */
	public T findByPrimaryKey(B b);

	/**
	 * Lista todos as entidades
	 * 
	 * @return uma lista de todas as entidades
	 */
	public List<T> find();

	/**
	 * Lista as entidades de acordo com a paginação informada
	 * 
	 * @param offset início da paginação
	 * @param limit quantidade de items por página
	 * @return lista de entidades
	 */
	public List<T> find(int offset, int limit);

	/**
	 * Quantidade de entidades na base
	 * 
	 * @return número de entidades na base
	 */
	public int count();

}
