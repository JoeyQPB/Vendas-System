package br.com.joey.domain;

public class ProdutoQuantidade {
	
	private Product produto;
	private Integer quantidade;
	private Double valorTotal;
	
	public ProdutoQuantidade() {
		this.quantidade = 0;
		this.valorTotal = 0.0;
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
