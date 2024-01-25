package br.com.joey.dao;

import br.com.joey.dao.generic.GenericDAO;
import br.com.joey.domain.Cliente;

public class clientDAO extends GenericDAO<Cliente, Long> implements IClientDAO {

	public clientDAO() {
		super();
	}

	@Override
	public Class<Cliente> getTipoClasse() {
		return Cliente.class;
	}

	@Override
	public void updateData(Cliente entity, Cliente entityCadastrado) {
		entityCadastrado.setCidade(entity.getCidade());
		entityCadastrado.setEnd(entity.getEnd());
		entityCadastrado.setEstado(entity.getEstado());
		entityCadastrado.setName(entity.getName());
		entityCadastrado.setNumero(entity.getNumero());
		entityCadastrado.setCel(entity.getCel());
	}

}
