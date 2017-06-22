package br.com.vitrinedecristal.service;

import java.util.List;

import br.com.vitrinedecristal.dao.ICategoryDAO;
import br.com.vitrinedecristal.exception.BusinessException;
import br.com.vitrinedecristal.model.Category;
import br.com.vitrinedecristal.service.base.IBaseService;
import br.com.vitrinedecristal.vo.CategoryVO;

/**
 * Define os contratos de lógica de negócio da entidade {@link Category}.
 */
public interface ICategoryService extends IBaseService<Long, Category, ICategoryDAO> {

	/**
	 * Obtém informações da categoria
	 * 
	 * @param id id da categoria
	 * @return informações da categoria
	 * @throws BusinessException
	 */
	// @Secured(ROLE_USER)
	CategoryVO getCategory(Long id) throws BusinessException;

	/**
	 * Cria a categoria
	 * 
	 * @param categoryVO informações para o cadastro da categoria
	 * @return categoria criada
	 * @throws BusinessException
	 */
	// @Secured(ROLE_ADMIN)
	CategoryVO createCategory(CategoryVO categoryVO) throws BusinessException;

	/**
	 * Atualiza as informações da categoria
	 * 
	 * @param categoriaVO informações para a alteração da categoria
	 * @return categoria alterada
	 * @throws BusinessException
	 */
	// @Secured(ROLE_ADMIN)
	CategoryVO updateCategory(CategoryVO categoryVO) throws BusinessException;

	/**
	 * Remove a categoria
	 * 
	 * @param id id da categoria a ser removida
	 * @throws BusinessException
	 */
	// @Secured(ROLE_ADMIN)
	void removeCategory(Long id) throws BusinessException;

	/**
	 * Lista as categorias
	 * 
	 * @return lista de categorias
	 * @throws BusinessException
	 */
	// @Secured(ROLE_USER)
	List<CategoryVO> listCategories() throws BusinessException;

	/**
	 * Lista as sub-categorias da categoria informada
	 * 
	 * @param parentId id da categoria pai
	 * @return lista de sub-categorias
	 * @throws BusinessException
	 */
	// @Secured(ROLE_USER)
	List<CategoryVO> listSubCategories(Long parentId) throws BusinessException;

}
