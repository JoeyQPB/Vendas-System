package br.com.joey;

import java.util.Collection;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Exceptions.UniqueValueNotFoundException;
import br.com.joey.dao.ClientDaoMock;
import br.com.joey.dao.IClientDAO;
import br.com.joey.domain.Cliente;

class ClienteDAOTeste {

	private IClientDAO clientDAO;
	private Cliente cliente;

	ClienteDAOTeste() {
		clientDAO = new ClientDaoMock();
	}

	@BeforeEach
	void init() {
		cliente = new Cliente("joey", 777L, 777l, "rua", 0, "City", "Estado");
	}

	@Test
	void create() throws UniqueValueNotFoundException {
		Assert.assertTrue(clientDAO.insert(cliente));
	}

	@Test
	void get() throws UniqueValueNotFoundException {
		clientDAO.insert(cliente);
		Cliente clientConsultado = clientDAO.get(cliente.getCpf());
		Assert.assertNotNull(clientConsultado);
		Assert.assertEquals(cliente, clientConsultado);
	}
	
	@Test
	void getAll() {
		Collection<Cliente> list =  clientDAO.getAll();
		Assert.assertNotNull(list);
	}
	
	@Test
	public void update() throws UniqueValueNotFoundException {
		cliente.setName("Novo nome");
		Boolean updatedCliente = clientDAO.update(cliente);
		Assert.assertTrue(updatedCliente);	
	}
	
	@Test
	void delete() {
		Assert.assertTrue(clientDAO.delete(cliente.getCpf()));
	}
}
