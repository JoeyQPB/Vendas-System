package br.com.joey.services;

import br.com.joey.domain.Cliente;
import br.com.joey.services.generic.IGenericService;

public interface IClientService extends IGenericService<Cliente, Long> {
	
	Cliente getByCpf(Long cpf);
}
