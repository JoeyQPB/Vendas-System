package br.com.joey.integracaoTestes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Exceptions.TestException;
import br.com.joey.dao.IProductDAO;
import br.com.joey.dao.ProductDAO;
import br.com.joey.domain.Product;

class ProductIntegracaoTestes {
	
	private IProductDAO dao;
	private Product produto;
	
	
	@BeforeEach
	void init() {
		dao = new ProductDAO();
		produto = new Product("AZN87", "Phone", "make calls", 1000.0);
	}
	
	@Test
	void insertTest() {
		assertTrue(insertProduct(produto));
		
		Product productDb = selectProduct(produto.getCode());

		assertNotNull(productDb);
		assertNotNull(productDb.getId());
		assertEquals(productDb, produto);
		assertEquals(productDb.toString(), produto.toString());
		
		assertTrue(deleteProduct(productDb.getCode()));
	}
	
	@Test
	void selectTest() {
		assertTrue(insertProduct(produto));
		
		Product productDb = selectProduct(produto.getCode());

		assertNotNull(productDb);
		assertNotNull(productDb.getId());
		assertEquals(productDb, produto);
		assertEquals(productDb.toString(), produto.toString());
		
		assertTrue(deleteProduct(productDb.getCode()));
	}
	
	@Test
	void selectAllTest() {
		assertTrue(insertProduct(produto));
		Product produto2 = new Product("AZN88", "Ipda", "Get Apps", 1200.0);
		assertTrue(insertProduct(produto2));
		
		Collection<Product> list = selectAllProduct();

		assertNotNull(list);
		assertEquals(list.size(), 2);
		assertTrue(list.contains(produto));
		assertTrue(list.contains(produto2));
		
		int countDel = 0;
		for (Product p : list) {
			assertTrue(deleteProduct(p.getCode()));
			countDel++;
		}
		assertEquals(countDel, 2);
	}
	
	@Test
	void updateTest() {
		assertTrue(insertProduct(produto));

		Product produtoBD = selectProduct(produto.getCode());
		assertNotNull(produtoBD);
		assertNotNull(produtoBD.getId());
		assertEquals(produto, produtoBD);
		assertEquals(produto.getCode(), produtoBD.getCode());
		assertEquals(produto.toString(), produtoBD.toString());

		String codeUnaterable = produtoBD.getCode();
		produtoBD.setPrice(700.0);
		produtoBD.setDescricao("get calls");

		assertTrue(updateTest(produtoBD));
		Product produtoBDUpdated = selectProduct(codeUnaterable);

		assertNotNull(produtoBDUpdated);
		assertNotNull(produtoBDUpdated.getId());
		assertEquals(produtoBDUpdated.getId(), produtoBD.getId());
		assertEquals(produtoBD, produtoBDUpdated);
		assertEquals(produtoBD.toString(), produtoBDUpdated.toString());

		assertEquals(produtoBDUpdated.getName(), "Phone");
		Double num = 700.0;
		assertEquals(num, produtoBDUpdated.getPrice());
		assertEquals(produtoBDUpdated.getDescricao(), "get calls");

		assertTrue(deleteProduct(codeUnaterable));
	}


	@Test
	void deleteTest() {
		assertTrue(insertProduct(produto));
		
		Product productDb = selectProduct(produto.getCode());

		assertNotNull(productDb);
		assertNotNull(productDb.getId());
		assertEquals(productDb, produto);
		assertEquals(productDb.toString(), produto.toString());
		
		assertTrue(deleteProduct(productDb.getCode()));
	}

	private boolean insertProduct(Product p) {
		try {
			return dao.insert(p);
		} catch (Exception e) {
			e.printStackTrace();
			throw new TestException(e.getMessage());
		}
	}
	
	private Product selectProduct(String code) {
		return dao.get(code);
	}
	
	private Collection<Product> selectAllProduct() {
		return dao.getAll();
	}
	
	private boolean updateTest(Product p) {
		try {
			return dao.update(p);
		} catch (Exception e) {
			e.printStackTrace();
			throw new TestException(e.getMessage());
		}
	}

	private Boolean deleteProduct(String code) {
		return dao.delete(code);
	}


}
