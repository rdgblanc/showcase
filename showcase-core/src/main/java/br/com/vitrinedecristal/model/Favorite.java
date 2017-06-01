package br.com.vitrinedecristal.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.vitrinedecristal.dao.base.IID;

@Entity
@Table(name = "TB_FAVORITO")
public class Favorite implements IID<Long> {

	@Id
	@Column(name = "ID", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "DT_INSERCAO", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtInsercao;

	@ManyToOne(targetEntity = Product.class)
	@JoinColumn(name = "ID_PRODUTO", nullable = false)
	private Product produto;

	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "ID_USUARIO", nullable = false)
	private User usuario;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDtInsercao() {
		return dtInsercao;
	}

	public void setDtInsercao(Date dtInsercao) {
		this.dtInsercao = dtInsercao;
	}

	public Product getProduto() {
		return produto;
	}

	public void setProduto(Product produto) {
		this.produto = produto;
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

}