package br.com.vitrinedecristal.dao;

import java.util.List;

import br.com.vitrinedecristal.dao.base.IBaseDAO;
import br.com.vitrinedecristal.model.Category;

/**
 * Define os contratos de acesso a dados da entidade {@link Category}.
 */
public interface ICategoryDAO extends IBaseDAO<Long, Category> {

	List<Category> findCategories();

	List<Category> findSubCategories(Long parentId);

}