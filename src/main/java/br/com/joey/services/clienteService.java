package br.com.joey.services;


import br.com.joey.dao.IClientDAO;
import br.com.joey.domain.Cliente;
import br.com.joey.services.generic.GenericService;

public class clienteService extends GenericService<Cliente, Long> implements IClientService {

	public clienteService(IClientDAO clientDAO) {
		super(clientDAO);
	}

	@Override
	public Cliente getByCpf(Long cpf) {
		return this.dao.get(cpf);
	}

}
