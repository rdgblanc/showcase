package br.com.vitrinedecristal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.vitrinedecristal.dao.base.IID;
import br.com.vitrinedecristal.enums.ImageTypeEnum;

@Entity
@Table(name = "TB_IMAGEM")
public class Image implements IID<Long> {

	@Id
	@Column(name = "ID", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Lob
	@Column(name = "CAMINHO", nullable = false)
	private String caminho;

	@Column(name = "TIPO", nullable = true)
	@Enumerated(value = EnumType.STRING)
	private ImageTypeEnum tipo;

	@ManyToOne(targetEntity = Product.class)
	@JoinColumn(name = "ID_PRODUTO", nullable = false)
	private Product produto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	public ImageTypeEnum getTipo() {
		return tipo;
	}

	public void setTipo(ImageTypeEnum tipo) {
		this.tipo = tipo;
	}

	public Product getProduto() {
		return produto;
	}

	public void setProduto(Product produto) {
		this.produto = produto;
	}

}