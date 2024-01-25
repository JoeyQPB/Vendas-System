package br.com.joey.services;

import br.com.joey.dao.IProductDAO;
import br.com.joey.domain.Product;
import br.com.joey.services.generic.GenericService;

public class ProductService extends GenericService<Product, String> implements IProductService {

	public ProductService(IProductDAO productDAO) {
		super(productDAO);
	}

}
