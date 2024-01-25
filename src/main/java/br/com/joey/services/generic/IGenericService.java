package br.com.joey.services.generic;

import java.io.Serializable;
import java.util.Collection;

import Exceptions.UniqueValueNotFoundException;
import br.com.joey.dao.Persistente;

public interface IGenericService<T extends Persistente, E extends Serializable> {

	Boolean create(T entity) throws UniqueValueNotFoundException;

	T get(E value);

	public Collection<T> getAll();

	Boolean delete(E value);

	Boolean update(T entity) throws UniqueValueNotFoundException;

}
