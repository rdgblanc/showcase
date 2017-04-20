package br.com.vitrinedecristal.dao.base;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

/**
 * Interface com os contratos básicos para uma classe DAO (Data Access Object).
 * 
 * @param <B> tipo da chave primária da entidade.
 * @param <T> tipo da entidade.
 */
public interface IBaseDAO<B extends Serializable, T extends IID<B>> {

	/**
	 * Retorna o entityManager.
	 * 
	 * @return o entityManager.
	 */
	EntityManager getEntityManager();

	/**
	 * Persiste a entidade na base.
	 * 
	 * @param t a entidade a ser persistida.
	 * @return a entidade persistida.
	 */
	T save(T t);

	/**
	 * Exclui a entidade da base.
	 * 
	 * @param t a entidade a ser excluída.
	 */
	void remove(T t);

	/**
	 * Pesquisa pela entidade por chave primária.
	 * 
	 * @param b a chave primária da entidade a ser pesquisada.
	 * @return a entidade pesquisada ou <code>null</code> caso a entidade não exista.
	 */
	T findByPrimaryKey(B b);

	/**
	 * Lista todas as entidades.
	 * 
	 * @return uma lista com todas as entidades.
	 */
	List<T> find();

	/**
	 * Lista as entidades.
	 * 
	 * @param offset a posição da primeira entidade retornada.
	 * @param limit o número máximo de entidades retornadas.
	 * @return uma lista com as entidades.
	 */
	List<T> find(int offset, int limit);

	/**
	 * Lista todas as entidades.
	 * <p>
	 * Os campos do parâmetro <code>filter</code> serão usados para montar a condição <code>WHERE</code>. Só serão usados os campos que:
	 * <ul>
	 * <li>Não são estáticos.
	 * <li>O valor é diferente de <code>null</code>.
	 * <li>Não possuem a anotação {@link javax.persistence.Transient}.
	 * </ul>
	 * 
	 * @param filter o objeto cujos campos serão usados como filtro. Caso seja <code>null</code> não será usado.
	 * @return uma lista com todas as entidades.
	 */
	List<T> find(T filter);

	/**
	 * Lista todas as entidades.
	 * <p>
	 * Os campos do parâmetro <code>filter</code> serão usados para montar a condição <code>WHERE</code>. Só serão usados os campos que:
	 * <ul>
	 * <li>Não são estáticos.
	 * <li>O valor é diferente de <code>null</code>.
	 * <li>Não possuem a anotação {@link javax.persistence.Transient}.
	 * </ul>
	 * 
	 * @param filter o objeto cujos campos serão usados como filtro. Caso seja <code>null</code> não será usado.
	 * @param offset a posição da primeira entidade retornada. Caso seja <code>null</code> não será usado.
	 * @param limit número máximo de entidades retornadas. Caso seja <code>null</code> não será usado.
	 * @return uma lista com todas as entidades.
	 */
	List<T> find(T filter, Integer offset, Integer limit);

	/**
	 * Conta a quantidade de entidades.
	 * 
	 * @return a quantidade de entidades.
	 */
	int count();

	/**
	 * Conta a quantidade de entidades.
	 * <p>
	 * Os campos do parâmetro <code>filter</code> serão usados para montar a condição <code>WHERE</code>. Só serão usados os campos que:
	 * <ul>
	 * <li>Não são estáticos.
	 * <li>O valor é diferente de <code>null</code>.
	 * <li>Não possuem a anotação {@link javax.persistence.Transient}.
	 * </ul>
	 * 
	 * @param filter o objeto cujos campos serão usados como filtro. Caso seja <code>null</code> não será usado.
	 * @return a quantidade de entidades.
	 */
	int count(T filter);

}
