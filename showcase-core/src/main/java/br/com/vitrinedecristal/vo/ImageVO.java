package br.com.vitrinedecristal.vo;

import br.com.vitrinedecristal.model.Image;
import br.com.vitrinedecristal.vo.base.BaseVO;

/**
 * Classe VO para representação de um objeto {@link Image}.
 */
public class ImageVO extends BaseVO<Image> {

	private static final long serialVersionUID = 1L;

	private Long id;

	private byte[] conteudo;

	private ProductVO produto;

	public ImageVO() {
	}

	/**
	 * Construtor para a criação via um objeto {@link Image}
	 * 
	 * @param image
	 */
	public ImageVO(Image image) {
		super(image);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getConteudo() {
		return conteudo;
	}

	public void setConteudo(byte[] conteudo) {
		this.conteudo = conteudo;
	}

	public ProductVO getProduto() {
		return produto;
	}

	public void setProduto(ProductVO produto) {
		this.produto = produto;
	}

}