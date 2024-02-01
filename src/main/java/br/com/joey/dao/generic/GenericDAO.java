package br.com.joey.dao.generic;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import Exceptions.UniqueValueNotFoundException;
import br.com.joey.annotations.UniqueValue;
import br.com.joey.dao.Persistente;

public abstract class GenericDAO<T extends Persistente, E extends Serializable> implements IGenericDAO<T, E> {

	private SingletonMap singletonMap;

	public abstract Class<T> getTipoClasse();

	public void updateData(T entity, T entityCadastrado) {
	}

	public GenericDAO() {
		this.singletonMap = SingletonMap.getInstance();
	}

	@SuppressWarnings("unchecked")
	public E getUniqueValue(T entity) throws UniqueValueNotFoundException {
		Field[] fields = entity.getClass().getDeclaredFields();
		E returnValue = null;
		for (Field field : fields) {
			if (field.isAnnotationPresent(UniqueValue.class)) {
				UniqueValue uniqueValue = field.getAnnotation(UniqueValue.class);
				String getterName = uniqueValue.value();

				try {
					Method method = entity.getClass().getMethod(getterName);
					returnValue = (E) method.invoke(entity);
					return returnValue;
				} catch (InvocationTargetException | IllegalArgumentException | IllegalAccessException
						| NoSuchMethodException | SecurityException e) {
					// TODO throws 
					e.printStackTrace();
				}

			}
		}
		
        if (returnValue == null) {
            String msg = "Chave principal do objeto " + entity.getClass() + " não encontrada";
            System.out.println("**** ERRO ****" + msg);
            throw new UniqueValueNotFoundException(msg);
        }
        return null;
	}
	
	
	@Override
	public Boolean insert(T entity) throws UniqueValueNotFoundException {
        Map<E, T> mapaInterno = getMap();
        E chave = getUniqueValue(entity);
        if (mapaInterno.containsKey(chave)) {
            return false;
        }

        mapaInterno.put(chave, entity);
        return true;
	}
	
	private Map<E, T> getMap() {
		@SuppressWarnings("unchecked")
		Map<E, T> mapInterno = (Map<E, T>) this.singletonMap.getMap().get(getTipoClasse());
		if (mapInterno == null) {
			mapInterno = new HashMap<>();
			this.singletonMap.getMap().put(getTipoClasse(), mapInterno);
		}
		return mapInterno;
	}
	
    @Override
    public Boolean delete(E value) {
        Map<E, T> mapaInterno = getMap();
        T objetoCadastrado = mapaInterno.get(value);
        if (objetoCadastrado != null) {
            mapaInterno.remove(value, objetoCadastrado);
            return true;
        }
        return false;
    }
	
    @Override
    public Boolean update(T entity) throws UniqueValueNotFoundException {
        Map<E, T> mapaInterno = getMap();
        E chave = getUniqueValue(entity);
        T objetoCadastrado = mapaInterno.get(chave);
        if (objetoCadastrado != null) {
            updateData(entity, objetoCadastrado);
            return true;
        }
        return false;
    }

    @Override
    public T get(E valor) {
        Map<E, T> mapaInterno = getMap();
        return mapaInterno.get(valor);
    }

    @Override
    public Collection<T> getAll() {
        Map<E, T> mapaInterno = getMap();
        return mapaInterno.values();
    }	
}
