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
@Table(name = "TB_LOGIN_USUARIO")
public class UserLogin implements IID<Long> {

	@Id
	@Column(name = "ID", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "IP", length = 45, nullable = false)
	private String ip;

	@Column(name = "DT_INSERCAO", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtInsercao;

	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "ID_USUARIO", nullable = false)
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getDtInsercao() {
		return dtInsercao;
	}

	public void setDtInsercao(Date dtInsercao) {
		this.dtInsercao = dtInsercao;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}