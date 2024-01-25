package br.com.joey.domain;

import java.util.Objects;

import br.com.joey.annotations.UniqueValue;
import br.com.joey.dao.Persistente;

public class Cliente implements Persistente{

	@UniqueValue(value="getCpf")
	private Long cpf;
	
	private String name;
	private Long cel;
	private String end;
	private Integer numero;
	private String cidade;
	private String estado;
	
	public Cliente() {}

	public Cliente(String name, Long cel, Long cpf, String end, Integer numero, String cidade, String estado) {
		this.name = name;
		this.cel = cel;
		this.cpf = cpf;
		this.end = end;
		this.numero = numero;
		this.cidade = cidade;
		this.estado = estado;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCel() {
		return cel;
	}

	public void setCel(Long cel) {
		this.cel = cel;
	}

	public Long getCpf() {
		return cpf;
	}

	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
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

	@Override
	public int hashCode() {
		return Objects.hash(cpf);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(cpf, other.cpf);
	}

	@Override
	public String toString() {
		return "Cliente [name=" + name + ", cel=" + cel + ", cpf=" + cpf + ", end=" + end + ", numero=" + numero
				+ ", cidade=" + cidade + ", estado=" + estado + "]";
	}
	
}
