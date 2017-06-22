package br.com.vitrinedecristal.service.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.vitrinedecristal.dao.IProductDAO;
import br.com.vitrinedecristal.dto.ProductDTO;
import br.com.vitrinedecristal.enums.ProductStatusEnum;
import br.com.vitrinedecristal.enums.RoleEnum;
import br.com.vitrinedecristal.exception.BusinessException;
import br.com.vitrinedecristal.exception.EntityNotFoundException;
import br.com.vitrinedecristal.exception.InvalidPermissionException;
import br.com.vitrinedecristal.model.Category;
import br.com.vitrinedecristal.model.Product;
import br.com.vitrinedecristal.security.util.AuthenticationUtils;
import br.com.vitrinedecristal.service.IImageService;
import br.com.vitrinedecristal.service.IProductService;
import br.com.vitrinedecristal.service.base.BaseService;
import br.com.vitrinedecristal.util.ParserUtil;
import br.com.vitrinedecristal.vo.ProductVO;

/**
 * Servico para realização de lógicas no negócio para a entidade {@link Product}
 */
public class ProductService extends BaseService<Long, Product, IProductDAO> implements IProductService {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(ProductService.class);

	@Autowired
	private IImageService imageService;

	public ProductService(IProductDAO productDAO) {
		super(productDAO);
	}

	@Override
	public ProductVO getProduct(Long id) throws BusinessException {
		logger.info("Obtendo produto pelo id: " + id);

		Product product = super.findByPrimaryKey(id);
		if (product == null) {
			throw new EntityNotFoundException("Não foi encontrado produto com o id informado.");
		}

		return ParserUtil.parse(product, ProductVO.class);
	}

	@Override
	@Transactional
	public ProductVO createProduct(ProductVO productVO) throws BusinessException {
		logger.info("Criando produto: " + productVO);

		if (productVO == null) {
			throw new IllegalArgumentException("A entidade produto não pode ser nula.");
		}

		if (productVO.getUsuario() == null || productVO.getUsuario().getId() == null) {
			throw new IllegalArgumentException("O usuário do produto não pode ser nulo ou conter id nulo.");
		}

		if (!AuthenticationUtils.listUserRoles().contains(RoleEnum.ROLE_ADMIN.toString()) && !productVO.getUsuario().getId().equals(AuthenticationUtils.getUserId())) {
			throw new InvalidPermissionException();
		}

		if (StringUtils.isBlank(productVO.getNome())) {
			throw new BusinessException("O campo nome é obrigatório");
		}

		if (StringUtils.isBlank(productVO.getDescricao())) {
			throw new BusinessException("O campo descrição é obrigatório");
		}

		if (productVO.getQuantidade() == null) {
			throw new BusinessException("O campo quantidade é obrigatório");
		}

		if (productVO.getEstadoConservacao() == null) {
			throw new BusinessException("O campo estado de conservação é obrigatório");
		}

		if (productVO.getTipoNegociacao() == null) {
			throw new BusinessException("O campo tipo de negociação é obrigatório");
		}

		if (productVO.getCategoria() == null) {
			throw new BusinessException("O campo categoria é obrigatório");
		}

		if (productVO.getCategoria().getCategoriaPai() == null) {
			throw new BusinessException("O campo sub-categoria é obrigatório");
		}

		Product product = ParserUtil.parse(productVO, Product.class);
		product.setStatus(ProductStatusEnum.ACTIVE);
		product.setDtAtualizacao(new Date());

		Product storedProduct = super.save(product);
		logger.info("Produto criado com sucesso!");

		return ParserUtil.parse(storedProduct, ProductVO.class);
	}

	@Override
	@Transactional
	public ProductVO updateProduct(ProductVO productVO) throws BusinessException {
		logger.info("Atualizando produto: " + productVO);

		if (productVO == null) {
			throw new IllegalArgumentException("A entidade produto não pode ser nula.");
		}

		if (productVO.getUsuario() == null || productVO.getUsuario().getId() == null) {
			throw new IllegalArgumentException("O usuário do produto não pode ser nulo ou conter id nulo.");
		}

		if (!AuthenticationUtils.listUserRoles().contains(RoleEnum.ROLE_ADMIN.toString()) && !productVO.getUsuario().getId().equals(AuthenticationUtils.getUserId())) {
			throw new InvalidPermissionException();
		}

		Product storedProduct = getDAO().findByPrimaryKey(productVO.getId());
		if (storedProduct == null) {
			throw new EntityNotFoundException("Não foi encontrado nenhum produto com o id informado.");
		}

		if (StringUtils.isNotBlank(productVO.getNome())) {
			storedProduct.setNome(productVO.getNome());
		}

		if (StringUtils.isNotBlank(productVO.getDescricao())) {
			storedProduct.setDescricao(productVO.getDescricao());
		}

		if (StringUtils.isNotBlank(productVO.getTamanho())) {
			storedProduct.setTamanho(productVO.getTamanho());
		}

		if (StringUtils.isNotBlank(productVO.getModelo())) {
			storedProduct.setModelo(productVO.getModelo());
		}

		if (StringUtils.isNotBlank(productVO.getMarca())) {
			storedProduct.setMarca(productVO.getMarca());
		}

		if (productVO.getPreco() != null) {
			storedProduct.setPreco(productVO.getPreco());
		}

		if (productVO.getQuantidade() != null) {
			storedProduct.setQuantidade(productVO.getQuantidade());
		}

		if (productVO.getEstadoConservacao() != null) {
			storedProduct.setEstadoConservacao(productVO.getEstadoConservacao());
		}

		if (productVO.getTipoNegociacao() != null) {
			storedProduct.setTipoNegociacao(productVO.getTipoNegociacao());
		}

		if (productVO.getCategoria() != null && productVO.getCategoria().getCategoriaPai() != null) {
			storedProduct.setCategoria(ParserUtil.parse(productVO.getCategoria(), Category.class));
		}

		if (AuthenticationUtils.listUserRoles().contains(RoleEnum.ROLE_ADMIN.toString())) {
			if (productVO.getClassificacao() != null) {
				storedProduct.setClassificacao(productVO.getClassificacao());
			}

			if (productVO.getStatus() != null) {
				storedProduct.setStatus(productVO.getStatus());
			}
		}

		storedProduct.setDtAtualizacao(new Date());
		storedProduct = super.save(storedProduct);
		logger.info("Produto atualizado com sucesso!");

		return ParserUtil.parse(storedProduct, ProductVO.class);
	}

	@Override
	@Transactional
	public void updateStatus(Long id, ProductStatusEnum status) throws BusinessException {
		if (id == null) {
			throw new IllegalArgumentException("O id do produto não pode ser nulo.");
		}

		if (status == null) {
			throw new IllegalArgumentException("O novo status do produto não pode ser nulo.");
		}

		logger.info("Alterando status do produto [" + id + "] para " + status);
		Product storedProduct = getDAO().findByPrimaryKey(id);
		if (storedProduct == null) {
			throw new EntityNotFoundException("Não foi encontrado nenhum produto para o id informado.");
		}

		// if (!AuthenticationUtils.listUserRoles().contains(RoleEnum.ROLE_ADMIN.toString()) && !storedProduct.getUsuario().getId().equals(AuthenticationUtils.getUserId())) {
		// throw new InvalidPermissionException();
		// }

		storedProduct.setStatus(status);
		storedProduct.setDtAtualizacao(new Date());
		super.save(storedProduct);

		logger.info("Status do produto atualizado com sucesso!");
	}

	@Override
	public List<ProductDTO> listProductByUser(Long userId) throws BusinessException {
		logger.info("Listando os produtos do usuário: " + userId);

		List<Product> listProduct = getDAO().findByUser(userId, Arrays.asList(ProductStatusEnum.ACTIVE));
		List<ProductDTO> listProductDTO = this.getListDTO(listProduct);

		logger.info("Produtos listados com sucesso!");

		return listProductDTO;
	}

	@Override
	public List<ProductDTO> listProductByCategory(Long categoryId) throws BusinessException {
		logger.info("Listando os produtos da categoria: " + categoryId);

		List<Product> listProduct = getDAO().findByCategory(categoryId, Arrays.asList(ProductStatusEnum.ACTIVE));
		List<ProductDTO> listProductDTO = this.getListDTO(listProduct);

		logger.info("Produtos listados com sucesso!");

		return listProductDTO;
	}

	@Override
	public List<ProductDTO> listProductByCategoryAnotherUser(Long categoryId, Long userId) throws BusinessException {
		logger.info("Listando os produtos da categoria e de outros usuários: " + categoryId + " - " + userId);

		List<Product> listProduct = getDAO().findByCategoryAnotherUser(categoryId, userId, Arrays.asList(ProductStatusEnum.ACTIVE));
		List<ProductDTO> listProductDTO = this.getListDTO(listProduct);

		logger.info("Produtos listados com sucesso!");

		return listProductDTO;
	}

	@Override
	public List<ProductDTO> listNewProducts() throws BusinessException {
		logger.info("Listando os novos produtos..");

		List<Product> listProduct = getDAO().findNewProducts(Arrays.asList(ProductStatusEnum.ACTIVE));
		List<ProductDTO> listProductDTO = this.getListDTO(listProduct);

		logger.info("Produtos listados com sucesso!");

		return listProductDTO;
	}

	@Override
	public List<ProductDTO> listNewProductsAnotherUser(Long userId) throws BusinessException {
		logger.info("Listando os novos produtos de outros usuários: " + userId);

		List<Product> listProduct = getDAO().findNewProductsAnotherUser(userId, Arrays.asList(ProductStatusEnum.ACTIVE));
		List<ProductDTO> listProductDTO = this.getListDTO(listProduct);

		logger.info("Produtos listados com sucesso!");

		return listProductDTO;
	}

	private List<ProductDTO> getListDTO(List<Product> listProduct) throws BusinessException {
		List<ProductDTO> listProductDTO = new ArrayList<ProductDTO>();
		for (Product product : listProduct) {
			ProductDTO productDTO = new ProductDTO();
			productDTO.setProduct(ParserUtil.parse(product, ProductVO.class));
			productDTO.setImages(this.imageService.listImagesByProduct(productDTO.getProduct().getId()));
			listProductDTO.add(productDTO);
		}

		return listProductDTO;
	}

}
