package br.com.vitrinedecristal.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.vitrinedecristal.dao.base.IID;
import br.com.vitrinedecristal.enums.MessageStatusEnum;

@Entity
@Table(name = "TB_MESSAGE")
public class Message implements IID<Long> {

	@Id
	@Column(name = "ID", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "TEXTO", length = 1000, nullable = false)
	private String texto;

	@Column(name = "DT_ATUALIZACAO", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtAtualizacao;

	@Column(name = "STATUS", nullable = false)
	@Enumerated(value = EnumType.STRING)
	private MessageStatusEnum status;

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

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Date getDtAtualizacao() {
		return dtAtualizacao;
	}

	public void setDtAtualizacao(Date dtAtualizacao) {
		this.dtAtualizacao = dtAtualizacao;
	}

	public MessageStatusEnum getStatus() {
		return status;
	}

	public void setStatus(MessageStatusEnum status) {
		this.status = status;
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