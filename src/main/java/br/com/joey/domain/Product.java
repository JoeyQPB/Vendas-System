package br.com.joey.domain;

import java.util.Objects;

import br.com.joey.annotations.Column;
import br.com.joey.annotations.Table;
import br.com.joey.annotations.UniqueValue;
import br.com.joey.dao.Persistente;

@Table("tb_produto")
public class Product implements Persistente {

	@Column(dbName= "id", setJavaName = "setId", getJavaName = "getId")
	private Long id;
	
	@UniqueValue("getCode")
	@Column(dbName= "code", setJavaName = "setCode", getJavaName = "getCode")
	private String code;
	
	@Column(dbName= "nome", setJavaName = "setName", getJavaName = "getName")
	private String name;
	
	@Column(dbName= "descricao", setJavaName = "setDescricao", getJavaName = "getDescricao")
	private String descricao;
	
	@Column(dbName= "preco", setJavaName = "setPrice", getJavaName = "getPrice")
	private Double price;
	
	public Product(String code, String name, String descricao, Double price) {
		this.code = code;
		this.name = name;
		this.descricao = descricao;
		this.price = price;
	}	 
	
	public Product(Long id, String code, String name, String descricao, Double price) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.descricao = descricao;
		this.price = price;
	}

	public Product() {
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public int hashCode() {
		return Objects.hash(code);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(code, other.code);
	}

	@Override
	public String toString() {
		return "Product [code=" + code + ", name=" + name + ", descricao=" + descricao + ", price=" + price + "]";
	}
}
