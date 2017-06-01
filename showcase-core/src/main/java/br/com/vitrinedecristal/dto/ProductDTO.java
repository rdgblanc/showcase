package br.com.vitrinedecristal.dto;

import java.io.Serializable;
import java.util.List;

import br.com.vitrinedecristal.vo.ProductVO;

@SuppressWarnings("serial")
public class ProductDTO implements Serializable {

	private ProductVO product;

	private List<ImageDTO> images;

	public ProductVO getProduct() {
		return product;
	}

	public void setProduct(ProductVO product) {
		this.product = product;
	}

	public List<ImageDTO> getImages() {
		return images;
	}

	public void setImages(List<ImageDTO> images) {
		this.images = images;
	}

}