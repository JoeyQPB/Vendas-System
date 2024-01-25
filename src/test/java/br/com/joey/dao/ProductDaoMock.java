package br.com.joey.dao;

import java.util.Arrays;
import java.util.Collection;

import Exceptions.UniqueValueNotFoundException;
import br.com.joey.domain.Product;

public class ProductDaoMock implements IProductDAO {

	private Collection<Product> list;

	@Override
	public Boolean insert(Product entity) throws UniqueValueNotFoundException {
		return true;
	}

	@Override
	public Product get(String value) {
		Product produto = new Product();
		produto.setCode(value);
		return produto;
	}

	@Override
	public Collection<Product> getAll() {
		list = Arrays.asList(new Product(), new Product());
		return list;
	}

	@Override
	public Boolean update(Product entity) throws UniqueValueNotFoundException {
		return true;
	}

	@Override
	public Boolean delete(String Value) {
		return true;
	}

}
