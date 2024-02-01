package br.com.joey.integracaoTestes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Exceptions.TestException;
import br.com.joey.dao.IClientDAO;
import br.com.joey.dao.clientDAO;
import br.com.joey.domain.Cliente;

class ClienteIntegracaoTestes {

	private IClientDAO dao;
	private Cliente client;
	private Cliente client2;
	
	@BeforeEach
	void init() {
		dao = new clientDAO();
		client = new Cliente("Jhon Doe", 5577988256741L, 11112312367L, "rua", 10, "city", "Estado");
	}

	@Test
	void insertTest() {
		assertTrue(InsertClientTest(client));
		
		Cliente clientDb = selectClientTest(client.getCpf());
		assertNotNull(clientDb);
		assertNotNull(clientDb.getId());
		assertEquals(client, clientDb);
		assertEquals(client.getCpf(), clientDb.getCpf());
		assertEquals(client.toString(), clientDb.toString());
		
		assertTrue(deleteClientTest(client.getCpf()));
	}
	
	@Test
	void selectTest() {
		assertTrue(InsertClientTest(client));
		
		Cliente clientDb = selectClientTest(client.getCpf());
		assertNotNull(clientDb);
		assertNotNull(clientDb.getId());
		assertEquals(client, clientDb);
		assertEquals(client.getCpf(), clientDb.getCpf());
		assertEquals(client.toString(), clientDb.toString());
		
		assertTrue(deleteClientTest(client.getCpf()));
	}
	
	@Test
	void selectAllTest() {
		client2 = new Cliente("Maria Doe", 5577988256741L, 11112312368L, "street", 7, "city", "Estado");
		assertTrue(InsertClientTest(client));
		assertTrue(InsertClientTest(client2));
		
		Collection<Cliente> list =  selectAllClientTest();
		assertNotNull(list);
		assertEquals(list.size(), 2);
		assertTrue(list.contains(client));
		assertTrue(list.contains(client2));
		
		int countDel = 0;
		for (Cliente c : list) {
			assertTrue(deleteClientTest(c.getCpf()));
			countDel++;
		}
		assertEquals(countDel, 2);
	}
	
	@Test
	void updateTest() {
		assertTrue(InsertClientTest(client));
		
		Cliente clientDb = selectClientTest(client.getCpf());
		assertNotNull(clientDb);
		assertNotNull(clientDb.getId());
		assertEquals(client, clientDb);
		assertEquals(client.getCpf(), clientDb.getCpf());
		assertEquals(client.toString(), clientDb.toString());
		
		Long cpfUnalterable = clientDb.getCpf();
		clientDb.setName("Joao Doe");
		clientDb.setNumero(7);
		clientDb.setEstado("Texas");
		
		assertTrue(updateClientTest(clientDb));
		Cliente clientDbUpdated = selectClientTest(cpfUnalterable);
		
		
		assertNotNull(clientDbUpdated);
		assertNotNull(clientDbUpdated.getId());
		assertEquals(clientDbUpdated.getId(), clientDb.getId());
		assertEquals(client, clientDbUpdated);
		assertEquals(clientDb, clientDbUpdated);

		assertEquals(clientDbUpdated.getName(), "Joao Doe");
		Integer num = 7;
		assertEquals(num, clientDbUpdated.getNumero());
		assertEquals(clientDbUpdated.getEstado(), "Texas");
		assertEquals(clientDbUpdated.getCel(), clientDb.getCel());
		assertEquals(clientDbUpdated.getCidade(), clientDb.getCidade());
		assertEquals(clientDbUpdated.getEnd(), clientDb.getEnd());

		assertTrue(deleteClientTest(cpfUnalterable));
	}
	
	@Test
	void deleteTest() {
		assertTrue(InsertClientTest(client));
		
		Cliente clientDb = selectClientTest(client.getCpf());
		assertNotNull(clientDb);
		assertNotNull(clientDb.getId());
		assertEquals(client, clientDb);
		assertEquals(client.getCpf(), clientDb.getCpf());
		assertEquals(client.toString(), clientDb.toString());
		
		assertTrue(deleteClientTest(client.getCpf()));
	}


	private Boolean InsertClientTest(Cliente c) {
		 try {
			 return dao.insert(c);
		 } catch (Exception e) {
			 e.printStackTrace();
			 throw new TestException(e.getMessage());
		 }
	}
	
	private Cliente selectClientTest(Long value) {
		return dao.get(value);
	}
		
	private Collection<Cliente> selectAllClientTest() {
			return dao.getAll();
	}
	
	private Boolean updateClientTest(Cliente c) {
		 try {
			 return dao.update(c);
		 } catch (Exception e) {
			 e.printStackTrace();
			 throw new TestException(e.getMessage());
		 }
	}
	
	private Boolean deleteClientTest(Long value) {
		return dao.delete(value);
	}
}
