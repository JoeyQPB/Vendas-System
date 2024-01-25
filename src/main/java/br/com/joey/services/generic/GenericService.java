package br.com.joey.services.generic;

import java.io.Serializable;
import java.util.Collection;

import Exceptions.UniqueValueNotFoundException;
import br.com.joey.dao.Persistente;
import br.com.joey.dao.generic.IGenericDAO;

public abstract class GenericService<T extends Persistente, E extends Serializable>  implements IGenericService<T,E> {

	protected IGenericDAO<T,E> dao;
	
	public GenericService(IGenericDAO<T,E> dao) {
		this.dao = dao;
	}
	
	@Override
	public Boolean create(T entity) throws UniqueValueNotFoundException {
		return this.dao.insert(entity);
	}

	@Override
	public Boolean delete(E valor) {
		return this.dao.delete(valor);
	}

	@Override
	public Boolean update(T entity) throws UniqueValueNotFoundException {
		return this.dao.update(entity);
	}

	@Override
	public T get(E valor) {
		return this.dao.get(valor);
	}

	@Override
	public Collection<T> getAll() {
		return this.dao.getAll();
	}
}
