package br.com.joey;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Exceptions.UniqueValueNotFoundException;
import br.com.joey.dao.ClientDaoMock;
import br.com.joey.dao.IClientDAO;
import br.com.joey.dao.Persistente;
import br.com.joey.domain.Cliente;
import br.com.joey.services.clienteService;
import br.com.joey.services.generic.IGenericService;

class ClienteServiceTeste {

	private IGenericService<Cliente, Long> clienteService;

	private Cliente cliente;

	public ClienteServiceTeste() {
		IClientDAO daoMock = new ClientDaoMock();
		clienteService = new clienteService(daoMock);
	}

	@BeforeEach
	public void init() {
		cliente = new Cliente("joey", 777L, 777l, "rua", 0, "City", "Estado");
	}

	@Test
	public void createCliente() throws UniqueValueNotFoundException {
		Assert.assertTrue(clienteService.create(cliente));
	}

	@Test
	public void getClient() {
		Persistente clientConsultado = clienteService.get(cliente.getCpf());
		Assert.assertEquals(cliente, clientConsultado);
		Assert.assertNotNull(clientConsultado);
	}
	
	@Test
	public void getAllClients() {
		Assert.assertNotNull(clienteService.getAll());
	}
	@Test
	public void updateClient() throws UniqueValueNotFoundException {
		cliente.setName("Novo nome");
		Boolean updatedCliente = clienteService.update(cliente);
		Assert.assertTrue(updatedCliente);
	}

	@Test
	public void deleteClient() {
		Assert.assertTrue(clienteService.delete(cliente.getCpf()));
	}

}
