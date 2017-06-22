package br.com.vitrinedecristal.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import br.com.vitrinedecristal.dao.base.IID;
import br.com.vitrinedecristal.enums.TokenEnum;

/**
 * Representa um token que controla as permissões do usuário
 */
@Entity
@Table(name = "TB_TOKEN")
public class Token implements IID<Long> {

	@Id
	@Column(name = "ID", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** String única. */
	@Column(name = "HASH")
	private String hash;

	/** Indica se o token foi usado. */
	@Column(name = "USADO")
	private boolean usado;

	/** Tipo do token. */
	@Column(name = "TIPO", nullable = false)
	@Enumerated(value = EnumType.STRING)
	private TokenEnum tipo;

	/** Data de inserção. */
	@Column(name = "DT_INSERCAO")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date dtInsercao;

	/** Data em que o token foi usado. */
	@Column(name = "DT_ATIVACAO")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date dtAtivacao;

	/** Data de expiração. */
	@Column(name = "DT_EXPIRACAO")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date dtExpiracao;

	/** Usuário associado. */
	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "ID_USUARIO")
	private User usuario;

	/**
	 * Gera o UUID antes de persistir na base de dados.
	 */
	@PrePersist
	private void generateHash() {
		if (this.hash == null) {
			this.hash = UUID.randomUUID().toString();
		}

		if (this.dtInsercao == null) {
			this.dtInsercao = new Date();
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public boolean isUsado() {
		return usado;
	}

	public void setUsado(boolean usado) {
		this.usado = usado;
	}

	public TokenEnum getTipo() {
		return tipo;
	}

	public void setTipo(TokenEnum tipo) {
		this.tipo = tipo;
	}

	public Date getDtInsercao() {
		return dtInsercao;
	}

	public void setDtInsercao(Date dtInsercao) {
		this.dtInsercao = dtInsercao;
	}

	public Date getDtAtivacao() {
		return dtAtivacao;
	}

	public void setDtAtivacao(Date dtAtivacao) {
		this.dtAtivacao = dtAtivacao;
	}

	public Date getDtExpiracao() {
		return dtExpiracao;
	}

	public void setDtExpiracao(Date dtExpiracao) {
		this.dtExpiracao = dtExpiracao;
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
