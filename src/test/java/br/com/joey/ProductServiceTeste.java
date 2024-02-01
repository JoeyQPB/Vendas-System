package br.com.joey;

import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Exceptions.UniqueValueNotFoundException;
import br.com.joey.dao.Persistente;
import br.com.joey.dao.ProductDaoMock;
import br.com.joey.domain.Product;
import br.com.joey.services.ProductService;
import br.com.joey.services.generic.IGenericService;

class ProductServiceTeste {
	
	private IGenericService<Product, String> service;
	private Product product;
	
	ProductServiceTeste() {
		ProductDaoMock daoMock = new ProductDaoMock();
		service = new ProductService(daoMock);
	}
	
	@BeforeEach
	void inti() {
		product = new Product("A1", "Nome do produto", "descrição", 10.0);
	}
	
	@Test
	void create() throws UniqueValueNotFoundException {
		assertTrue(service.create(product));
	}
	
	@Test
	void get() {
		Persistente productConsultado = service.get(product.getCode());
		Assert.assertEquals(product, productConsultado);
		Assert.assertNotNull(productConsultado);
	}
	
	@Test
	void getAll() {
		Assert.assertNotNull(service.getAll());
	}
	
	@Test
	void update() throws UniqueValueNotFoundException {
		product.setName("Novo nome");
		Boolean updaetedProduct = service.update(product);
		Assert.assertTrue(updaetedProduct);
	}
	
	@Test
	void delete() {
		assertTrue(service.delete(product.getCode()));
	}

}
