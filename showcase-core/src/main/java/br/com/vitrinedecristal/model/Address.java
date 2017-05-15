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
import br.com.vitrinedecristal.enums.AddressStatusEnum;

@Entity
@Table(name = "TB_ENDERECO")
public class Address implements IID<Long> {

	@Id
	@Column(name = "ID", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "DESCRICAO", length = 45, nullable = true)
	private String descricao;

	@Column(name = "CEP", length = 20, nullable = false)
	private String cep;

	@Column(name = "ENDERECO", length = 200, nullable = true)
	private String endereco;

	@Column(name = "NUMERO", nullable = true)
	private Integer numero;

	@Column(name = "COMPLEMENTO", length = 20, nullable = true)
	private String complemento;

	@Column(name = "BAIRRO", length = 100, nullable = true)
	private String bairro;

	@Column(name = "CIDADE", length = 100, nullable = true)
	private String cidade;

	@Column(name = "ESTADO", length = 45, nullable = true)
	private String estado;

	@Column(name = "DT_ATUALIZACAO", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtAtualizacao;

	@Column(name = "STATUS", nullable = false)
	@Enumerated(value = EnumType.STRING)
	private AddressStatusEnum status;

	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "ID_USUARIO")
	private User usuario;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getDtAtualizacao() {
		return dtAtualizacao;
	}

	public void setDtAtualizacao(Date dtAtualizacao) {
		this.dtAtualizacao = dtAtualizacao;
	}

	public AddressStatusEnum getStatus() {
		return status;
	}

	public void setStatus(AddressStatusEnum status) {
		this.status = status;
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

}