package br.com.joey.dao;

import java.util.Arrays;
import java.util.Collection;

import Exceptions.UniqueValueNotFoundException;
import br.com.joey.domain.Cliente;

public class ClientDaoMock implements IClientDAO {
	
	private Collection<Cliente> list;

	@Override
	public Boolean insert(Cliente entity) throws UniqueValueNotFoundException {
		return true;
	}

	@Override
	public Cliente get(Long value) {
		Cliente cliente = new Cliente();
		cliente.setCpf(value);
		return cliente;
	}

	@Override
	public Collection<Cliente> getAll() {
		list = Arrays.asList(new Cliente(), new Cliente());
		return list;
	}

	@Override
	public Boolean update(Cliente entity) throws UniqueValueNotFoundException {
		return true;
	}

	@Override
	public Boolean delete(Long Value) {
		return true;
	}

}

