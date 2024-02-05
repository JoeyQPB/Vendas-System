package br.com.joey.domain;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import br.com.joey.annotations.Column;
import br.com.joey.annotations.Table;
import br.com.joey.annotations.UniqueValue;
import br.com.joey.dao.Persistente;

@Table("tb_venda")
public class Sell implements Persistente {

	public enum Status {
		INICIADA, CONCLUIDA, CANCELADA;

		public static Status getByName(String value) {
			for (Status status : Status.values()) {
				if (status.name().equals(value)) {
					return status;
				}
			}
			return null;
		}
	}

	@Column(dbName = "id", setJavaName = "setId", getJavaName = "getId")
	private Long id;

	@UniqueValue("getCode")
	@Column(dbName = "codigo", setJavaName = "setCode", getJavaName = "getCode")
	private String code;

	@Column(dbName = "id_cliente_fk", setJavaName = "setClientefk", getJavaName = "getClientefk")
	private Cliente cliente;

	@Column(dbName = "id_produtos_fk", setJavaName = "setProdutosfk", getJavaName = "getProdutosfk")
	private Set<ProdutoQuantidade> produtos;

	@Column(dbName = "total", setJavaName = "setTotal", getJavaName = "getTotal")
	private Double total;

	@Column(dbName = "sellDate", setJavaName = "setsellDate", getJavaName = "getsellDate")
	private Instant sellDate;

	@Column(dbName = "status", setJavaName = "setStatus", getJavaName = "getStatus")
	private Status status;

	public Sell(String code, Cliente cliente, Instant sellDate, Status status) {
		this.produtos = new HashSet<>();
		this.code = code;
		this.cliente = cliente;
		this.sellDate = sellDate;
		this.status = status;
	}

	public Sell() {
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

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Instant getSellDate() {
		return sellDate;
	}

	public void setSellDate(Instant sellDate) {
		this.sellDate = sellDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Set<ProdutoQuantidade> getProdutos() {
		return produtos;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
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
		Sell other = (Sell) obj;
		return Objects.equals(code, other.code);
	}

	@Override
	public String toString() {
		return "Sell [code=" + code + ", cliente=" + cliente + ", produtos=" + produtos.toArray().toString()
				+ ", total=" + total + ", sellDate=" + sellDate + ", status=" + status + "]";
	}

	public void adicionarProduto(Product produto, Integer quantidade) {
		validarStatus();
		Optional<ProdutoQuantidade> op = produtos.stream()
				.filter(filter -> filter.getProduto().getCode().equals(produto.getCode())).findAny();

		if (op.isPresent()) {
			ProdutoQuantidade produtpQtd = op.get();
			produtpQtd.adicionar(quantidade);
		} else {
			ProdutoQuantidade prod = new ProdutoQuantidade();
			prod.setProduto(produto);
			prod.adicionar(quantidade);
			produtos.add(prod);
		}
		recalcularValorTotalVenda();
	}

	private void validarStatus() {
		if (this.status == Status.CONCLUIDA) {
			throw new UnsupportedOperationException("IMPOSSÍVEL ALTERAR VENDA FINALIZADA");
		}
	}

	public void removerProduto(Product produto, Integer quantidade) {
		validarStatus();
		Optional<ProdutoQuantidade> op = produtos.stream()
				.filter(filter -> filter.getProduto().getCode().equals(produto.getCode())).findAny();

		if (op.isPresent()) {
			ProdutoQuantidade produtpQtd = op.get();
			if (produtpQtd.getQuantidade() > quantidade) {
				produtpQtd.remover(quantidade);
				recalcularValorTotalVenda();
			} else {
				produtos.remove(op.get());
				recalcularValorTotalVenda();
			}

		}
	}

	public void removerTodosProdutos() {
		validarStatus();
		produtos.clear();
		total = 0.0;
	}

	public Integer getQuantidadeTotalProdutos() {
		int result = produtos.stream().reduce(0,
				(partialCountResult, prod) -> partialCountResult + prod.getQuantidade(), Integer::sum);
		return result;
	}

	public void recalcularValorTotalVenda() {
		validarStatus();
		double valorTotal = 0.0;
		for (ProdutoQuantidade prod : this.produtos) {
			valorTotal = valorTotal + prod.getValorTotal();
		}
		this.total = valorTotal;
	}

	public void setProdutos(Set<ProdutoQuantidade> produtos) {
		this.produtos = produtos;
	}
}
