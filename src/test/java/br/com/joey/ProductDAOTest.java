package br.com.joey;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Collection;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import Exceptions.UniqueValueNotFoundException;
import br.com.joey.dao.IProductDAO;
import br.com.joey.dao.ProductDaoMock;
import br.com.joey.domain.Product;

class ProductDAOTest {
	
	private IProductDAO dao;
	private Product product;
	
	public ProductDAOTest() {
		dao = new ProductDaoMock();
	}
	
	@BeforeEach
	void inti() {
		product = new Product("A1", "Nome do produto", "descrição", BigDecimal.TEN);
	}
	
	@Test
	void create() throws UniqueValueNotFoundException {
		assertTrue(dao.insert(product));
	}
	
	@Test
	void get() throws UniqueValueNotFoundException {
		dao.insert(product);
		Product prodConsultod = dao.get(product.getCode());
		Assert.assertNotNull(prodConsultod);
		Assert.assertEquals(product, prodConsultod);
	}
	
	@Test
	void getAll() {
		Collection<Product> list =  dao.getAll();
		Assert.assertNotNull(list);
	}
	
	@Test
	void update() throws UniqueValueNotFoundException {
		product.setName("Novo Nome");
		Boolean result = dao.update(product);
		Assert.assertTrue(result);
		Assert.assertEquals(product.getName(), "Novo Nome");
	}
	
	@Test
	void delete() {
		assertTrue(dao.delete(product.getCode()));
	}

}