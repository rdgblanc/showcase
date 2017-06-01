package br.com.vitrinedecristal.service.bean;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.vitrinedecristal.dao.IImageDAO;
import br.com.vitrinedecristal.dto.ImageDTO;
import br.com.vitrinedecristal.enums.ImageTypeEnum;
import br.com.vitrinedecristal.exception.BusinessException;
import br.com.vitrinedecristal.exception.EntityNotFoundException;
import br.com.vitrinedecristal.model.Image;
import br.com.vitrinedecristal.model.Product;
import br.com.vitrinedecristal.service.IImageService;
import br.com.vitrinedecristal.service.IProductService;
import br.com.vitrinedecristal.service.base.BaseService;
import br.com.vitrinedecristal.util.ParserUtil;
import br.com.vitrinedecristal.vo.ImageVO;
import br.com.vitrinedecristal.vo.ProductVO;

/**
 * Servico para realização de lógicas no negócio para a entidade {@link Image}
 */
public class ImageService extends BaseService<Long, Image, IImageDAO> implements IImageService {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(ImageService.class);

	@Autowired
	private IProductService productService;

	public ImageService(IImageDAO imageDAO) {
		super(imageDAO);
	}

	@Override
	public ImageVO getImage(Long id) throws BusinessException {
		logger.info("Obtendo imagem pelo id: " + id);

		Image image = super.findByPrimaryKey(id);
		if (image == null) {
			throw new EntityNotFoundException("Não foi encontrada imagem com o id informado.");
		}

		return ParserUtil.parse(image, ImageVO.class);
	}

	@Override
	@Transactional
	public ImageVO createImage(Long productId, String filename, Integer fileIndex) throws BusinessException {
		logger.info("Criando imagem para o produto: " + productId);

		Image imagem = new Image();
		imagem.setCaminho(filename);

		if (fileIndex == 0) {
			imagem.setTipo(ImageTypeEnum.MAIN);
		} else {
			imagem.setTipo(ImageTypeEnum.ADDITIONAL);
		}

		ProductVO productVO = this.productService.getProduct(productId);
		imagem.setProduto(ParserUtil.parse(productVO, Product.class));

		logger.info("Criando imagem: " + imagem);
		Image storedImage = super.save(imagem);
		logger.info("Imagem criada com sucesso!");

		return ParserUtil.parse(storedImage, ImageVO.class);
	}

	@Override
	@Transactional
	public void removeImage(Long id) throws BusinessException {
		logger.info("Removendo imagem pelo id: " + id);
		Image image = new Image();
		image.setId(id);
		super.remove(image);
		logger.info("Imagem [" + id + "] removida com sucesso!");
	}

	@Override
	public List<ImageDTO> listImagesByProduct(Long productId) throws BusinessException {
		logger.info("Listando as imagens do produto: " + productId);
		List<Image> listImages = getDAO().findByProduct(productId);
		logger.info("Imagens listadas com sucesso!");

		return ParserUtil.parse(listImages, ImageDTO.class);
	}
}
