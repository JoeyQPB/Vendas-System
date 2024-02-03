package br.com.joey.domain;

import br.com.joey.annotations.Column;
import br.com.joey.annotations.Table;

@Table("tb_produtoQuantidade")
public class ProdutoQuantidade {
	
	@Column(dbName= "id", setJavaName = "setId", getJavaName = "getId")
	private Long id;
	
	@Column(dbName= "id_produto_fk", setJavaName = "setProdutofk", getJavaName = "getProdutofk")
	private Product produto;
	
	@Column(dbName= "quantidade", setJavaName = "setQuantidade", getJavaName = "getQuantidade")
	private Integer quantidade;
	
	@Column(dbName= "valorTotal", setJavaName = "setValorTotal", getJavaName = "getValorTotal")
	private Double valorTotal;
	
	public ProdutoQuantidade() {
		this.quantidade = 0;
		this.valorTotal = 0.0;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Product getProduto() {
		return produto;
	}

	public void setProduto(Product produto) {
		this.produto = produto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}
	
	public void adicionar(Integer quantidade) {
		this.quantidade += quantidade;
		Double novoValor = this.produto.getPrice()*quantidade;
		Double novoTotal = this.valorTotal + novoValor;
		this.valorTotal = novoTotal;
	}
	
	public void remover(Integer quantidade) {
		this.quantidade -= quantidade;
		Double novoValor = this.produto.getPrice() * quantidade;
		this.valorTotal = this.valorTotal - novoValor;
	}

	@Override
	public String toString() {
		return "ProdutoQuantidade [produto=" + produto + ", quantidade=" + quantidade + ", valorTotal=" + valorTotal
				+ "]";
	}
}
