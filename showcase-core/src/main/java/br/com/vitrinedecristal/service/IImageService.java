package br.com.vitrinedecristal.service;

import java.io.InputStream;
import java.util.List;

import org.springframework.security.access.annotation.Secured;

import br.com.vitrinedecristal.dao.IImageDAO;
import br.com.vitrinedecristal.exception.BusinessException;
import br.com.vitrinedecristal.model.Image;
import br.com.vitrinedecristal.service.base.IBaseService;
import br.com.vitrinedecristal.vo.ImageVO;

/**
 * Define os contratos de lógica de negócio da entidade {@link Image}.
 */
public interface IImageService extends IBaseService<Long, Image, IImageDAO> {

	/**
	 * Obtém informações da imagem
	 * 
	 * @param id id da imagem
	 * @return informações da imagem
	 * @throws BusinessException
	 */
	@Secured(ROLE_USER)
	ImageVO getImage(Long id) throws BusinessException;

	/**
	 * Cria a imagem
	 * 
	 * @param productId id do produto da imagem
	 * @param imagemInpuStream bytes da imagem
	 * @return imagem criada
	 * @throws BusinessException
	 */
	@Secured(ROLE_USER)
	ImageVO createImage(Long productId, InputStream imageInputStream) throws BusinessException;

	/**
	 * Remove a imagem
	 * 
	 * @param id id da imagem a ser removida
	 * @throws BusinessException
	 */
	@Secured(ROLE_USER)
	void removeImage(Long id) throws BusinessException;

	/**
	 * Lista as imagens do produto
	 * 
	 * @param productId id do produto
	 * @return lista de imagens do produto
	 * @throws BusinessException
	 */
	@Secured(ROLE_USER)
	List<ImageVO> listImagesByProduct(Long productId) throws BusinessException;
}
