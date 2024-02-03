package br.com.joey;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

class SellDAOTest {

	private ISellDAO vendaDao;
	private IClientDAO clienteDao;
	private IProductDAO ProductDao;

	private Cliente cliente;
	private Product product;

	public SellDAOTest() {
		vendaDao = new SellDAO();
		clienteDao = new clientDAO();
		ProductDao = new ProductDAO();
	}

	private Product cadastrarProduct(String code, Double price) throws UniqueValueNotFoundException {
		Product Product = new Product(code, "Product 1", "descrição produto", price);
		ProductDao.insert(Product);
		return Product;
	}

	private Cliente cadastrarCliente() throws UniqueValueNotFoundException {
		Cliente cliente = new Cliente("joey", 777L, 777L, "end", 10, "City", "BA");
		clienteDao.insert(cliente);
		return cliente;
	}

	private Sell criarVenda(String code) {
		Sell sell = new Sell(code, this.cliente, Instant.now(), Status.INICIADA);
		sell.adicionarProduto(this.product, 2);
		return sell;
	}

	@BeforeEach
	public void init() throws UniqueValueNotFoundException {
		this.cliente = cadastrarCliente();
		this.product = cadastrarProduct("A1", 10.0);
	}

	@Test
	public void search() throws UniqueValueNotFoundException {
		Sell sell = criarVenda("A1");
		Boolean retorno = vendaDao.insert(sell);
		assertTrue(retorno);
		Sell vendaConsultada = vendaDao.select(sell.getCode());
		assertNotNull(vendaConsultada);
		assertEquals(sell.getCode(), vendaConsultada.getCode());
	}
	@Test
	public void salvar() throws UniqueValueNotFoundException {
		Sell venda = criarVenda("A2");
		Boolean retorno = vendaDao.insert(venda);
		assertTrue(retorno);
		assertTrue(venda.getTotal().equals(20.0));
		assertTrue(venda.getStatus().equals(Status.INICIADA));
	} 
	
	
	@Test
	public void cancelarVenda() throws UniqueValueNotFoundException {
		String codigoVenda = "A3";
		Sell venda = criarVenda(codigoVenda);
		Boolean retorno = vendaDao.insert(venda);
		assertTrue(retorno);
		assertNotNull(venda);
		assertEquals(codigoVenda, venda.getCode());
		
		venda.setStatus(Status.CANCELADA);
		vendaDao.update(venda);
		
		Sell vendaConsultada = vendaDao.select(codigoVenda);
		assertEquals(codigoVenda, vendaConsultada.getCode());
		assertEquals(Status.CANCELADA, vendaConsultada.getStatus());
	}
	
	
	@Test
	public void adicionarMaisProdutosDoMesmo() throws UniqueValueNotFoundException {
		String codigoVenda = "A4";
		Sell venda = criarVenda(codigoVenda);
		Boolean retorno = vendaDao.insert(venda);
		assertTrue(retorno);
		assertNotNull(venda);
		assertEquals(codigoVenda, venda.getCode());
		
		Sell vendaConsultada = vendaDao.select(codigoVenda);
		vendaConsultada.adicionarProduto(this.product, 1);
		
		assertTrue(venda.getQuantidadeTotalProdutos() == 3);
		assertTrue(venda.getTotal().equals(30.0));
		assertTrue(venda.getStatus().equals(Status.INICIADA));
	} 
	
	@Test
	public void adicionarMaisProdutosDiferentes() throws UniqueValueNotFoundException {
		String codigoVenda = "A5";
		Sell venda = criarVenda(codigoVenda);
		Boolean retorno = vendaDao.insert(venda);
		assertTrue(retorno);
		assertNotNull(venda);
		assertEquals(codigoVenda, venda.getCode());
		
		Product prod = cadastrarProduct(codigoVenda, 50.0);
		assertNotNull(prod);
		assertEquals(codigoVenda, prod.getCode());
		
		Sell vendaConsultada = vendaDao.select(codigoVenda);
		vendaConsultada.adicionarProduto(prod, 1);
		
		assertTrue(venda.getQuantidadeTotalProdutos() == 3);
		assertTrue(venda.getTotal().equals(70.0));
		assertTrue(venda.getStatus().equals(Status.INICIADA));
	} 
	
	
	@Test
	public void salvarProdutoExistente() throws UniqueValueNotFoundException {
		Sell venda = criarVenda("A6");
		Boolean retorno = vendaDao.insert(venda);
		assertTrue(retorno);
	
		Boolean retorno1 = vendaDao.insert(venda);
		assertFalse(retorno1);
		assertTrue(venda.getStatus().equals(Status.INICIADA));
	} 
	
	@Test
	public void removerProduto() throws UniqueValueNotFoundException {
		String codigoVenda = "A7";
		Sell venda = criarVenda(codigoVenda);
		Boolean retorno = vendaDao.insert(venda);
		assertTrue(retorno);
		assertNotNull(venda);
		assertEquals(codigoVenda, venda.getCode());
		
		Product prod = cadastrarProduct(codigoVenda, 50.0);
		assertNotNull(prod);
		assertEquals(codigoVenda, prod.getCode());
		
		Sell vendaConsultada = vendaDao.select(codigoVenda);
		vendaConsultada.adicionarProduto(prod, 1);
		assertTrue(venda.getQuantidadeTotalProdutos() == 3);
		assertTrue(venda.getTotal().equals(70.0));
		
		
		vendaConsultada.removerProduto(prod, 1);
		assertTrue(venda.getQuantidadeTotalProdutos() == 2);
		assertTrue(venda.getTotal().equals(20.0));
		assertTrue(venda.getStatus().equals(Status.INICIADA));
	} 
	
	@Test
	public void removerApenasUmProduto() throws UniqueValueNotFoundException {
		String codigoVenda = "A8";
		Sell venda = criarVenda(codigoVenda);
		Boolean retorno = vendaDao.insert(venda);
		assertTrue(retorno);
		assertNotNull(venda);
		assertEquals(codigoVenda, venda.getCode());
		
		Product prod = cadastrarProduct(codigoVenda, 50.0);
		assertNotNull(prod);
		assertEquals(codigoVenda, prod.getCode());
		
		Sell vendaConsultada = vendaDao.select(codigoVenda);
		vendaConsultada.adicionarProduto(prod, 1);
		assertTrue(venda.getQuantidadeTotalProdutos() == 3);
		assertTrue(venda.getTotal().equals(70.0));
		
		
		vendaConsultada.removerProduto(prod, 1);
		assertTrue(venda.getQuantidadeTotalProdutos() == 2);
		assertTrue(venda.getTotal().equals(20.0));
		assertTrue(venda.getStatus().equals(Status.INICIADA));
	} 
	
	@Test
	public void removerTodosProdutos() throws UniqueValueNotFoundException {
		String codigoVenda = "A9";
		Sell venda = criarVenda(codigoVenda);
		Boolean retorno = vendaDao.insert(venda);
		assertTrue(retorno);
		assertNotNull(venda);
		assertEquals(codigoVenda, venda.getCode());
		
		Product prod = cadastrarProduct(codigoVenda, 50.0);
		assertNotNull(prod);
		assertEquals(codigoVenda, prod.getCode());
		
		Sell vendaConsultada = vendaDao.select(codigoVenda);
		vendaConsultada.adicionarProduto(prod, 1);
		assertTrue(venda.getQuantidadeTotalProdutos() == 3);
		assertTrue(venda.getTotal().equals(70.0));
		
		
		vendaConsultada.removerTodosProdutos();
		assertTrue(venda.getQuantidadeTotalProdutos() == 0);
		assertTrue(venda.getTotal().equals(0.0));
		assertTrue(venda.getStatus().equals(Status.INICIADA));
	} 
}
