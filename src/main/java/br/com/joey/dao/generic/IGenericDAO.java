package br.com.joey.dao.generic;

import java.io.Serializable;
import java.util.Collection;

import Exceptions.UniqueValueNotFoundException;
import br.com.joey.dao.Persistente;

public interface IGenericDAO<T extends Persistente, E extends Serializable> {
	
	public Boolean insert(T entity) throws UniqueValueNotFoundException;
	public T get(E Value);
	public Collection<T> getAll();
	public Boolean update(T entity) throws UniqueValueNotFoundException;
	public Boolean delete(E Value);
	
}
