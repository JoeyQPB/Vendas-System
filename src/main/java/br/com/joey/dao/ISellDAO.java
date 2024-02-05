package br.com.joey.dao;

import br.com.joey.dao.generic.IGenericDAO;
import br.com.joey.domain.Sell;

public interface ISellDAO extends IGenericDAO<Sell, String> {
	
	public void atualiarDados(Sell entity, Sell entityCadastrado);
	
	public void finalizarVenda(Sell venda);
	
	public void cancelarVenda(Sell venda);
	
	public void executeDelete(String sql);
}
