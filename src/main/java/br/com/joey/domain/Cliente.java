package br.com.joey.domain;

import java.util.Date;
import java.util.Objects;

import br.com.joey.annotations.Column;
import br.com.joey.annotations.Table;
import br.com.joey.annotations.UniqueValue;
import br.com.joey.dao.Persistente;

@Table("tb_cliente")
public class Cliente implements Persistente {

	@Column(dbName= "id", setJavaName = "setId", getJavaName = "getId")
	private Long id;
	
	@UniqueValue(value="getCpf")
	@Column(dbName= "cpf", setJavaName = "setCpf", getJavaName = "getCpf")
	private Long cpf;
	
	@Column(dbName= "nome", setJavaName = "setName", getJavaName = "getName")
	private String name;
	
	@Column(dbName= "tel", setJavaName = "setCel", getJavaName = "getCel")
	private Long cel;
	
	@Column(dbName= "endereco", setJavaName = "setEnd", getJavaName = "getEnd")
	private String end;
	
	@Column(dbName= "numero", setJavaName = "setNumero", getJavaName = "getNumero")
	private Integer numero;
	
	@Column(dbName= "cidade", setJavaName = "setCidade", getJavaName = "getCidade")
	private String cidade;
	
	@Column(dbName= "estado", setJavaName = "setEstado", getJavaName = "getEstado")
	private String estado;
	
	@Column(dbName= "createdAt", setJavaName = "setCreatedAt", getJavaName = "getCreatedAt")
	private Date createdAt;

	public Cliente() {} 

	public Cliente(String name, Long cel, Long cpf, String end, Integer numero, String cidade, String estado) {
		this.name = name;
		this.cel = cel;
		this.cpf = cpf; 
		this.end = end;
		this.numero = numero;
		this.cidade = cidade;
		this.estado = estado;
		this.createdAt = new Date();
	}
	
	public Cliente(Long id, String name, Long cel, Long cpf, String end, Integer numero, String cidade, String estado) {
		this.id = id;
		this.name = name;
		this.cel = cel;
		this.cpf = cpf;
		this.end = end;
		this.numero = numero;
		this.cidade = cidade;
		this.estado = estado;
		this.createdAt = new Date();
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id =  id;
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
	
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
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
		return "Cliente [cpf=" + cpf + ", name=" + name + ", cel=" + cel + ", end=" + end + ", numero="
				+ numero + ", cidade=" + cidade + ", estado=" + estado + "]";
	}
}
