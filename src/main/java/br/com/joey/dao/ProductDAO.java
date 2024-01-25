package br.com.joey.dao;

import br.com.joey.dao.generic.GenericDAO;
import br.com.joey.domain.Product;

public class ProductDAO extends GenericDAO<Product, String> implements IProductDAO{

	@Override
	public Class<Product> getTipoClasse() {
		return Product.class;
	}

	@Override
	public void updateData(Product entity, Product entityCadastrado) {
		entityCadastrado.setDescricao(entity.getDescricao());
		entityCadastrado.setName(entity.getName());
		entityCadastrado.setPrice(entity.getPrice());
	}

}
