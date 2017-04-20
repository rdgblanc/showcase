package br.com.vitrinedecristal.service.base;

import java.io.Serializable;
import java.util.List;

import br.com.vitrinedecristal.dao.base.IBaseDAO;
import br.com.vitrinedecristal.dao.base.IID;

/**
 * Estabelece os comportamentos básicos de negócio de uma entidade.
 * 
 * @param <B> tipo da chave primária da entidade.
 * @param <T> tipo da entidade.
 * @param <D> tipo do DAO.
 */
public abstract class BaseService<B extends Serializable, T extends IID<B>, D extends IBaseDAO<B, T>> implements Serializable, IBaseService<B, T, D> {

	private static final long serialVersionUID = 1L;

	private D dao;

	/**
	 * Construtor
	 * 
	 * @param dao - DAO que será utilizado referente a entidade manipulada
	 */
	public BaseService(D dao) {
		this.dao = dao;
	}

	/**
	 * Retorna o dao para manipulação da entidade
	 * 
	 * @return
	 */
	public D getDAO() {
		return this.dao;
	}

	public T save(T t) {
		return this.getDAO().save(t);
	}

	public void remove(T t) {
		this.getDAO().remove(t);
	}

	public T findByPrimaryKey(B b) {
		return this.getDAO().findByPrimaryKey(b);
	}

	public List<T> find() {
		return this.getDAO().find();
	}

	public List<T> find(int offset, int limit) {
		return this.getDAO().find(offset, limit);
	}

	public int count() {
		return this.getDAO().count();
	}

}
