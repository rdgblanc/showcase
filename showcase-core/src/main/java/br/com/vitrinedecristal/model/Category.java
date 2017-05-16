package br.com.vitrinedecristal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.vitrinedecristal.dao.base.IID;

@Entity
@Table(name = "TB_CATEGORIA")
public class Category implements IID<Long> {

	@Id
	@Column(name = "ID", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NOME", length = 45, nullable = false)
	private String nome;

	@ManyToOne(targetEntity = Category.class)
	@JoinColumn(name = "ID_CATEGORIA_PAI", nullable = true)
	private Category categoriaPai;

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

	public Category getCategoriaPai() {
		return categoriaPai;
	}

	public void setCategoriaPai(Category categoriaPai) {
		this.categoriaPai = categoriaPai;
	}

}