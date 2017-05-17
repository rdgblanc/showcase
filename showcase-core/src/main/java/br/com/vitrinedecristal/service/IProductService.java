package br.com.vitrinedecristal.service;

import java.util.List;

import org.springframework.security.access.annotation.Secured;

import br.com.vitrinedecristal.dao.IProductDAO;
import br.com.vitrinedecristal.enums.ProductStatusEnum;
import br.com.vitrinedecristal.exception.BusinessException;
import br.com.vitrinedecristal.model.Product;
import br.com.vitrinedecristal.service.base.IBaseService;
import br.com.vitrinedecristal.vo.ProductVO;

/**
 * Define os contratos de lógica de negócio da entidade {@link Product}.
 */
public interface IProductService extends IBaseService<Long, Product, IProductDAO> {

	/**
	 * Obtém informações do produto
	 * 
	 * @param id id do produto
	 * @return informações do produto
	 * @throws BusinessException
	 */
	@Secured(ROLE_USER)
	ProductVO getProduct(Long id) throws BusinessException;

	/**
	 * Cria o produto
	 * 
	 * @param productVO informações para o cadastro do produto
	 * @return produto criado
	 * @throws BusinessException
	 */
	@Secured(ROLE_USER)
	ProductVO createProduct(ProductVO productVO) throws BusinessException;

	/**
	 * Atualiza as informações do produto
	 * 
	 * @param productVO informações para a alteração do produto
	 * @return produto alterado
	 * @throws BusinessException
	 */
	@Secured(ROLE_USER)
	ProductVO updateProduct(ProductVO productVO) throws BusinessException;

	/**
	 * Altera o status do produto
	 * 
	 * @param id id do produto a ser alterado o status
	 * @param status novo status do produto
	 * @throws BusinessException
	 */
	@Secured(ROLE_USER)
	void updateStatus(Long id, ProductStatusEnum status) throws BusinessException;

	/**
	 * Lista os produtos do usuários
	 * 
	 * @param userId id do usuário
	 * @return lista de produtos do usuário
	 * @throws BusinessException
	 */
	@Secured(ROLE_USER)
	List<ProductVO> listProductByUser(Long userId) throws BusinessException;

}
