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
@Table(name = "TB_CHAT")
public class Chat implements IID<Long> {

	@Id
	@Column(name = "ID", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "TEXTO", length = 1000, nullable = false)
	private String texto;

	@Column(name = "DT_INSERCAO", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtInsercao;

	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "ID_USUARIO_ORIGEM", nullable = false)
	private User usuarioOrigem;

	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "ID_USUARIO_DESTINO", nullable = false)
	private User usuarioDestino;

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

	public Date getDtInsercao() {
		return dtInsercao;
	}

	public void setDtInsercao(Date dtInsercao) {
		this.dtInsercao = dtInsercao;
	}

	public User getUsuarioOrigem() {
		return usuarioOrigem;
	}

	public void setUsuarioOrigem(User usuarioOrigem) {
		this.usuarioOrigem = usuarioOrigem;
	}

	public User getUsuarioDestino() {
		return usuarioDestino;
	}

	public void setUsuarioDestino(User usuarioDestino) {
		this.usuarioDestino = usuarioDestino;
	}

}