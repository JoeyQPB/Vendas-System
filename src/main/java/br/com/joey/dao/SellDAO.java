package br.com.joey.dao;

import br.com.joey.dao.generic.GenericDAO;
import br.com.joey.domain.Sell;

public class SellDAO extends GenericDAO<Sell, String> implements ISellDAO{

	@Override
	public Class<Sell> getTipoClasse() {
		return Sell.class;
	}

	@Override
	public void updateData(Sell entity, Sell entityCadastrado) {
		entityCadastrado.setStatus(entity.getStatus());
	}

	@Override
	public Boolean delete(String code) {
		throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITIDA");
	}

}
