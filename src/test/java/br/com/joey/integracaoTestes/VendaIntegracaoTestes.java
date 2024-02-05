package br.com.joey.integracaoTestes;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.Collection;

import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Exceptions.DbException;
import Exceptions.UniqueValueNotFoundException;
import br.com.joey.dao.IClientDAO;
import br.com.joey.dao.IProductDAO;
import br.com.joey.dao.ISellDAO;
import br.com.joey.dao.ProductDAO;
import br.com.joey.dao.SellDAO;
import br.com.joey.dao.clientDAO;
import br.com.joey.domain.Cliente;
import br.com.joey.domain.Product;
import br.com.joey.domain.Sell;
import br.com.joey.domain.Sell.Status;

class VendaIntegracaoTestes {
	
	private ISellDAO sellDAO;
	private IClientDAO clientDAO;
	private IProductDAO productDAO;
	
	private Cliente client;
	private Product product;

	public VendaIntegracaoTestes() {
		sellDAO = new SellDAO();
		clientDAO = new clientDAO();
		productDAO = new ProductDAO();
	}
	
	@BeforeEach
	void init() {
		createClient();
		product = createProduct("TTTSSS", 1000.0);

		try {
			clientDAO.insert(client);
			productDAO.insert(product);
		} catch (UniqueValueNotFoundException e) {
			e.printStackTrace();
			throw new DbException(e.getMessage());
		}
	}
	
	@After
	public void end() {
		excluirVendas();
		excluirProdutos();
		clientDAO.delete(client.getCpf());
	}

	@Test
	void getVenda() {
		Sell sell = createSell("A1");
		Boolean retorno = createSellTest(sell);
		assertTrue(retorno);
		Sell vendaConsultada = gettSellTest(sell.getCode());
		assertNotNull(vendaConsultada);
		assertEquals(sell.getCode(), vendaConsultada.getCode());
	}
	
	@Test
	public void salvar() {
		Sell venda = createSell("A2");
		Boolean retorno = createSellTest(venda);
		assertTrue(retorno);
		
		assertTrue(venda.getTotal().equals(2000.0));
		assertTrue(venda.getStatus().equals(Status.INICIADA));
		
		Sell vendaConsultada = gettSellTest(venda.getCode());
		assertNotNull(vendaConsultada.getId());
		assertEquals(venda.getCode(), vendaConsultada.getCode());
	} 
	
	@Test
	public void cancelarVenda() {
		String codigoVenda = "A3";
		Sell venda = createSell(codigoVenda);
		Boolean retorno = createSellTest(venda);
		assertTrue(retorno);
		assertNotNull(venda);
		assertEquals(codigoVenda, venda.getCode());
		
		cancelarVendaTest(venda);
		
		Sell vendaConsultada = gettSellTest(codigoVenda);
		assertEquals(codigoVenda, vendaConsultada.getCode());
		assertEquals(Status.CANCELADA, vendaConsultada.getStatus());
	}
	
	@Test
	public void adicionarMaisProdutosDoMesmo() {
		String codigoVenda = "A4";
		Sell venda = createSell(codigoVenda);
		Boolean retorno = createSellTest(venda);
		assertTrue(retorno);
		assertNotNull(venda);
		assertEquals(codigoVenda, venda.getCode());
		
		Sell vendaConsultada = gettSellTest(codigoVenda);
		vendaConsultada.adicionarProduto(product, 1);
		
		assertTrue(vendaConsultada.getQuantidadeTotalProdutos() == 3);
		Double valorTotal = 3000.0;
		assertTrue(vendaConsultada.getTotal().equals(valorTotal));
		assertTrue(vendaConsultada.getStatus().equals(Status.INICIADA));
	} 

	@Test
	public void adicionarMaisProdutosDiferentes() throws UniqueValueNotFoundException {
		String codigoVenda = "A5";
		Sell venda = createSell(codigoVenda);
		Boolean retorno = createSellTest(venda);
		assertTrue(retorno);
		assertNotNull(venda);
		assertEquals(codigoVenda, venda.getCode());
		
		Product prod = createProduct(codigoVenda, 500.0);
		productDAO.insert(prod);
		assertNotNull(prod);
		assertEquals(codigoVenda, prod.getCode());
		
		Sell vendaConsultada = gettSellTest(codigoVenda);
		vendaConsultada.adicionarProduto(prod, 1);
		
		assertTrue(vendaConsultada.getQuantidadeTotalProdutos() == 3);
		Double valorTotal = 2500.0;
		assertTrue(vendaConsultada.getTotal().equals(valorTotal));
		assertTrue(vendaConsultada.getStatus().equals(Status.INICIADA));
	} 
	
	@Test
	public void salvarVendaMesmoCodigoExistente() {
		Sell venda = createSell("A6");
		Boolean retorno = createSellTest(venda);
		assertTrue(retorno);
	
		Boolean retorno1 = createSellTest(venda);
		assertFalse(retorno1);
		assertTrue(venda.getStatus().equals(Status.INICIADA));
	} 
	
	@Test
	public void removerProduto() throws UniqueValueNotFoundException {
		String codigoVenda = "A7";
		Sell venda = createSell(codigoVenda);
		Boolean retorno = createSellTest(venda);
		assertTrue(retorno);
		assertNotNull(venda);
		assertEquals(codigoVenda, venda.getCode());
		
		Product prod = createProduct(codigoVenda, 500.0);
		productDAO.insert(prod);
		assertNotNull(prod);
		assertEquals(codigoVenda, prod.getCode());
		
		Sell vendaConsultada = gettSellTest(codigoVenda);
		vendaConsultada.adicionarProduto(prod, 1);
		assertTrue(vendaConsultada.getQuantidadeTotalProdutos() == 3);
		Double valorTotal = 2500.0;
		assertTrue(vendaConsultada.getTotal().equals(valorTotal));
		
		
		vendaConsultada.removerProduto(prod, 1);
		assertTrue(vendaConsultada.getQuantidadeTotalProdutos() == 2);
		valorTotal = 2500.0;
		assertTrue(vendaConsultada.getTotal().equals(valorTotal));
		assertTrue(vendaConsultada.getStatus().equals(Status.INICIADA));
	}
	
	@Test
	public void removerApenasUmProduto() throws UniqueValueNotFoundException {
		String codigoVenda = "A8";
		Sell venda = createSell(codigoVenda);
		Boolean retorno = createSellTest(venda);
		assertTrue(retorno);
		assertNotNull(venda);
		assertEquals(codigoVenda, venda.getCode());
		
		Product prod = createProduct(codigoVenda, 500.0);
		productDAO.insert(prod);
		assertNotNull(prod);
		assertEquals(codigoVenda, prod.getCode());
		
		Sell vendaConsultada = gettSellTest(codigoVenda);
		vendaConsultada.adicionarProduto(prod, 1);
		assertTrue(vendaConsultada.getQuantidadeTotalProdutos() == 3);
		Double valorTotal = 2500.0;
		assertTrue(vendaConsultada.getTotal().equals(valorTotal));
		
		
		vendaConsultada.removerProduto(prod, 1);
		assertTrue(vendaConsultada.getQuantidadeTotalProdutos() == 2);
		valorTotal = 2000.0;
		assertTrue(vendaConsultada.getTotal().equals(valorTotal));
		assertTrue(vendaConsultada.getStatus().equals(Status.INICIADA));
	} 
	
	@Test
	public void removerTodosProdutos() throws UniqueValueNotFoundException {
		String codigoVenda = "A9";
		Sell venda = createSell(codigoVenda);
		Boolean retorno = createSellTest(venda);
		assertTrue(retorno);
		assertNotNull(venda);
		assertEquals(codigoVenda, venda.getCode());
		
		Product prod = createProduct(codigoVenda, 500.0);
		productDAO.insert(prod);
		assertEquals(codigoVenda, prod.getCode());
		
		Sell vendaConsultada = gettSellTest(codigoVenda);
		vendaConsultada.adicionarProduto(prod, 1);
		assertTrue(vendaConsultada.getQuantidadeTotalProdutos() == 3);
		Double valorTotal = 2500.0;
		assertTrue(vendaConsultada.getTotal().equals(valorTotal));
		
		
		vendaConsultada.removerTodosProdutos();
		assertTrue(vendaConsultada.getQuantidadeTotalProdutos() == 0);
		assertTrue(vendaConsultada.getTotal().equals(0.0));
		assertTrue(vendaConsultada.getStatus().equals(Status.INICIADA));
	} 
	
	@Test
	public void finalizarVenda() {
		String codigoVenda = "A10";
		Sell venda = createSell(codigoVenda);
		Boolean retorno = createSellTest(venda);
		assertTrue(retorno);
		assertNotNull(venda);
		assertEquals(codigoVenda, venda.getCode());
		
		cancelarVendaTest(venda);
		
		Sell vendaConsultada = gettSellTest(codigoVenda);
		assertEquals(venda.getCode(), vendaConsultada.getCode());
		assertEquals(Status.CONCLUIDA, vendaConsultada.getStatus());
	}
	
	@Test
	public void tentarAdicionarProdutosVendaFinalizada() {
		String codigoVenda = "A11";
		Sell venda = createSell(codigoVenda);
		Boolean retorno = createSellTest(venda);
		assertTrue(retorno);
		assertNotNull(venda);
		assertEquals(codigoVenda, venda.getCode());
		
		sellDAO.finalizarVenda(venda);
		Sell vendaConsultada = gettSellTest(codigoVenda);
		assertEquals(venda.getCode(), vendaConsultada.getCode());
		assertEquals(Status.CONCLUIDA, vendaConsultada.getStatus());
		
		vendaConsultada.adicionarProduto(this.product, 1);
		
	}
	
	private void createClient() {
		client =  new Cliente("Jhon Doe", 5577988256741L, 11112312367L, "rua", 10, "city", "Estado");
	}
	
	private Product createProduct(String code, Double value) {
		return new Product(code, "Phone", "make calls", value, 10);
	}
	
	private Sell createSell(String code) {
		Sell sell = new Sell(code, this.client, Instant.now(), Status.INICIADA);
		sell.adicionarProduto(this.product, 2);
		return sell;
	}
	
	private Boolean createSellTest(Sell entity) {
		try {
			return sellDAO.insert(entity);
		} catch (UniqueValueNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Sell gettSellTest(String value) {
		return sellDAO.select(value);
	}
	
	private void cancelarVendaTest(Sell entity) {
		sellDAO.cancelarVenda(entity);
	}
	
	private void excluirProdutos() {
		Collection<Product> list = this.productDAO.selectAll();
		for (Product prod : list) {
			this.productDAO.delete(prod.getCode());
		}
	}

	private void excluirVendas() {
		String sqlProd = "DELETE FROM TB_PRODUTO_QUANTIDADE";
		sellDAO.executeDelete(sqlProd);
		
		String sqlV = "DELETE FROM TB_VENDA";
		sellDAO.executeDelete(sqlV);
	}
}
