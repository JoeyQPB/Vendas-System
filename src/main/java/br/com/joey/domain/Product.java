package br.com.joey.domain;

import java.math.BigDecimal;
import java.util.Objects;

import br.com.joey.annotations.UniqueValue;
import br.com.joey.dao.Persistente;

public class Product implements Persistente {

	@UniqueValue("getCode")
	private String code;
	private String name;
	private String descricao;
	private BigDecimal price;
	
	public Product(String code, String name, String descricao, BigDecimal price) {
		super();
		this.code = code;
		this.name = name;
		this.descricao = descricao;
		this.price = price;
	}

	public Product() {
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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
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
