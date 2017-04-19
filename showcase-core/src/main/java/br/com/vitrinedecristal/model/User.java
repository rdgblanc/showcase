package br.com.vitrinedecristal.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.vitrinedecristal.dao.IID;
import br.com.vitrinedecristal.enums.GenderEnum;
import br.com.vitrinedecristal.enums.RoleEnum;
import br.com.vitrinedecristal.enums.UserStatusEnum;

@Entity
@Table(name = "TB_USUARIO")
public class User implements IID<Long> {

	@Id
	@Column(name = "ID", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NOME", length = 200, nullable = true)
	private String nome;

	@Column(name = "EMAIL", length = 200, nullable = false)
	private String email;

	@Column(name = "SENHA", length = 45, nullable = false)
	private String senha;

	@Column(name = "SEXO", nullable = true)
	@Enumerated(value = EnumType.STRING)
	private GenderEnum sexo;

	@Column(name = "TELEFONE", length = 20, nullable = true)
	private String telefone;

	@Column(name = "DT_NASCIMENTO", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtNascimento;

	@Column(name = "CLASSIFICACAO", nullable = true)
	private Float classificacao;

	@Column(name = "DT_ATUALIZACAO", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtAtualizacao;

	@Column(name = "STATUS", nullable = false)
	@Enumerated(value = EnumType.STRING)
	private UserStatusEnum status;

	@ElementCollection(targetClass = RoleEnum.class, fetch = FetchType.EAGER)
	@JoinTable(name = "TB_FUNCAO_USUARIO", joinColumns = @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID"))
	@Column(name = "FUNCAO", nullable = false)
	@Enumerated(EnumType.STRING)
	private List<RoleEnum> roles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public GenderEnum getSexo() {
		return sexo;
	}

	public void setSexo(GenderEnum sexo) {
		this.sexo = sexo;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Date getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(Date dtNascimento) {
		this.dtNascimento = dtNascimento;
	}

	public Float getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(Float classificacao) {
		this.classificacao = classificacao;
	}

	public Date getDtAtualizacao() {
		return dtAtualizacao;
	}

	public void setDtAtualizacao(Date dtAtualizacao) {
		this.dtAtualizacao = dtAtualizacao;
	}

	public UserStatusEnum getStatus() {
		return status;
	}

	public void setStatus(UserStatusEnum status) {
		this.status = status;
	}

	public List<RoleEnum> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleEnum> roles) {
		this.roles = roles;
	}

}