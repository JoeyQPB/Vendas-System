package br.com.joey.dao.generic;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import Exceptions.DbException;
import Exceptions.GetValuesException;
import Exceptions.UniqueValueNotFoundException;
import Exceptions.UnkowFieldTypeException;
import bd_jdbc.ConnectionJdbcFactory;
import br.com.joey.annotations.Column;
import br.com.joey.annotations.Table;
import br.com.joey.annotations.UniqueValue;
import br.com.joey.dao.Persistente;

public abstract class GenericDAO<T extends Persistente, E extends Serializable> implements IGenericDAO<T, E> {

	public abstract Class<T> getTipoClasse();
	public Class<T> clazz = null;

	public void updateData(T entity, T entityCadastrado) {
	}

	public GenericDAO() {
		clazz = getTipoClasse();
	}
	
	@Override
	public Boolean insert(T entity) {
		Connection connection = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		
		try {
			connection = ConnectionJdbcFactory.getConnection();
			connection.setAutoCommit(false);
			stm = connection.prepareStatement(getInsertQuerySQL(entity), Statement.RETURN_GENERATED_KEYS);
			stm.executeUpdate();
			connection.commit();
			rs = stm.getGeneratedKeys();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e.getMessage());
		} finally {
			ConnectionJdbcFactory.closeConnection(connection, stm, rs);
		}
	}
	
	@Override
	public T select(E value) {
		Connection connection = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		T entity = null;
		try {
			connection = ConnectionJdbcFactory.getConnection();
			stm = connection.prepareStatement(getSelectQuery(value));
			rs = stm.executeQuery();
			
			if (rs.next()) {
				entity = getTipoClasse().getConstructor(null).newInstance(null);
				Field[] fields = entity.getClass().getDeclaredFields();
				for (Field field : fields) {
					if (field.isAnnotationPresent(Column.class)) {
						Column annotationColumn = field.getAnnotation(Column.class);
						String fieldDBName = annotationColumn.dbName();
						String fieldSetJavaMethodName = annotationColumn.setJavaName();
						Class<?> fieldTypeClass = field.getType();
						try {
							Method setMethod = entity.getClass().getMethod(fieldSetJavaMethodName, fieldTypeClass);
							setValues(entity, setMethod, fieldTypeClass, rs, fieldDBName);
						} catch (UnkowFieldTypeException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
		                	throw new UnkowFieldTypeException("ERRO CONSULTANDO OBJETO ", e);
		                } 
					}
				}
				return entity;
			} else return null;

		} catch (SQLException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			throw new DbException(e.getMessage());
		} finally {
			ConnectionJdbcFactory.closeConnection(connection, stm, rs);
		}
	}

	@Override
	public Collection<T> selectAll() {
		Connection connection = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		T entity = null;
		List<T> list = new ArrayList<>(); 
		try {
			connection = ConnectionJdbcFactory.getConnection();
			stm = connection.prepareStatement(getSelectAllQuery());
			rs = stm.executeQuery();
			
			while (rs.next()) {
				entity = getTipoClasse().getConstructor(null).newInstance(null);
				Field[] fields = entity.getClass().getDeclaredFields();
				for (Field field : fields) {
					if (field.isAnnotationPresent(Column.class)) {
						Column annotationColumn = field.getAnnotation(Column.class);
						String fieldDBName = annotationColumn.dbName();
						String fieldSetJavaMethodName = annotationColumn.setJavaName();
						Class<?> fieldTypeClass = field.getType();
						try {
							Method setMethod = entity.getClass().getMethod(fieldSetJavaMethodName, fieldTypeClass);
							setValues(entity, setMethod, fieldTypeClass, rs, fieldDBName);
						} catch (UnkowFieldTypeException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
		                	throw new UnkowFieldTypeException("ERRO CONSULTANDO OBJETO ", e);
		                } 
					}
				}
				list.add(entity);
			} 
			return list;
		} catch (SQLException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			throw new DbException(e.getMessage());
		} finally {
			ConnectionJdbcFactory.closeConnection(connection, stm, rs);
		}
	}

	@Override
	public Boolean update(T entity) {
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			connection = ConnectionJdbcFactory.getConnection();
			stm = connection.prepareStatement(getUpdateQuery(entity));
			int rowsAffected = stm.executeUpdate();
			if (rowsAffected > 1) {
				throw new DbException("Foi atualizado mais de um dado!");
			}
			return rowsAffected > 0 ? true : false;
		} catch(SQLException | UniqueValueNotFoundException e) {
			e.printStackTrace();
			throw new DbException(e.getMessage());
		} finally {
			ConnectionJdbcFactory.closeConnection(connection, stm, null);
		}
	}

	@Override
	public Boolean delete(E value) {
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			connection = ConnectionJdbcFactory.getConnection();
			stm = connection.prepareStatement(getDeleteQuery(value));
			int rowsAffected = stm.executeUpdate();
			if (rowsAffected > 1) {
				throw new DbException("Foi excluido mais de um dado!");
			}
			return rowsAffected > 0 ? true : false;
		} catch(SQLException e) {
			e.printStackTrace();
			throw new DbException(e.getMessage());
		} finally {
			ConnectionJdbcFactory.closeConnection(connection, stm, null);
		}
	}
	

	@SuppressWarnings({ "unchecked", "unused" })
	public E getUniqueValue(T entity) throws UniqueValueNotFoundException {
		Field[] fields = entity.getClass().getDeclaredFields();
		E value = null;
		for (Field field : fields) {
			if (field.isAnnotationPresent(UniqueValue.class)) {
				UniqueValue uniqueValue = field.getAnnotation(UniqueValue.class);
				String getterName = uniqueValue.value();

				try {
					Method method = entity.getClass().getMethod(getterName);
					value = (E) method.invoke(entity);
					if (value.getClass() == String.class) value = (E) ("'" + value + "'");
					return value;
				} catch (InvocationTargetException | IllegalArgumentException | IllegalAccessException
						| NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
					throw new GetValuesException(e.getMessage());
				}

			}
		}
		
        if (value == null) {
            String msg = "Chave principal do objeto " + entity.getClass() + " não encontrada";
            System.out.println("**** ERRO ****" + msg);
            throw new UniqueValueNotFoundException(msg);
        }
		return value;
	}
	
	private String getTableName() {
		Table tableAnnotation = this.clazz.getDeclaredAnnotation(Table.class);
		String tableName = tableAnnotation.value();
		
		if (tableName.split("_")[0].equalsIgnoreCase("tb")) return tableName;
		return null;
	}

	private String getColumns() {
		StringBuilder sb = new StringBuilder();

		Field[] fields = this.clazz.getDeclaredFields(); 
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].isAnnotationPresent(Column.class)) {
				Column columnAnnotation = fields[i].getDeclaredAnnotation(Column.class);
				if (i != fields.length-1) {
					sb.append(columnAnnotation.dbName() + ",");
				} else {
					sb.append(columnAnnotation.dbName());
				}
			}
		}
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	private String getColunmsValues(T entity) {
		StringBuilder sb = new StringBuilder();
		String sequenceSQLName = "sq_" + getTableName().split("_")[1];
		sb.append("nextVal('" + sequenceSQLName + "'), ");

		Field[] fields = this.clazz.getDeclaredFields();

		// skip id
		for (int i = 1; i < fields.length; i++) {
			if (fields[i].isAnnotationPresent(Column.class)) {
				Column columnAnnotation = fields[i].getDeclaredAnnotation(Column.class);
				String methodName = columnAnnotation.getJavaName();
				try {
					Method method = this.clazz.getDeclaredMethod(methodName);
					E value = (E) method.invoke(entity);
					if (value.getClass() == String.class) value = (E) ("'" + value + "'");
					if (i != fields.length - 1) {
						sb.append(value + ", ");
					} else {
						sb.append(value);
					}
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					e.printStackTrace();
					throw new GetValuesException(e.getMessage());
				}
			}
		}
		return sb.toString();
	}
	
	private String getInsertQuerySQL(T entity) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO " + getTableName() + " ");
		sb.append("( " + getColumns() + " ) ");
		sb.append("VALUES (" + getColunmsValues(entity) + ");");
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	private String getSelectQuery(E value) {
		if (value.getClass() == String.class) value = (E) ("'" + value + "'");
		return "SELECT * FROM " + getTableName() + " WHERE " + getUniqueValueName() + " = " + value + ";";
	}

	private String getUniqueValueName() {
		Field[] fields = this.clazz.getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(UniqueValue.class)) {
				return field.getAnnotation(Column.class).dbName();
			}
		}
		return null;
	}

	private void setValues(T entity, Method setMethod, Class<?> fieldTypeClass, ResultSet rs, String fieldDBName)
			throws SQLException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (fieldTypeClass.equals(String.class)) {
			String value = rs.getString(fieldDBName);
			setMethod.invoke(entity, value);
		}
		else if (fieldTypeClass.equals(Integer.class)) {
    		Integer value = rs.getInt(fieldDBName);
    		setMethod.invoke(entity, value);
		}
		else if (fieldTypeClass.equals(Long.class)) {
			Long value = rs.getLong(fieldDBName);
			setMethod.invoke(entity, value);
		}
    	else if (fieldTypeClass.equals(Double.class)) {
			Double value = rs.getDouble(fieldDBName);
    		setMethod.invoke(entity, value);
		}
    	else if (fieldTypeClass.equals(Short.class)) {
			Short value = rs.getShort(fieldDBName);
    		setMethod.invoke(entity, value);
		} 
		else {
			throw new UnkowFieldTypeException("TIPO DE CLASSE NÃO CONHECIDO: " + fieldTypeClass);
		}
	}
	
	private String getSelectAllQuery() {
		return "SELECT * FROM " + getTableName() + ";";
	}
	
	private String getUpdateQuery(T entity) throws UniqueValueNotFoundException {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE " + getTableName() + " ");
		sb.append("SET " + getFieldAndFieldValue(entity) + " ");
		sb.append("WHERE " + getUniqueValueName() + " = " + getUniqueValue(entity) + ";");
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	private String getFieldAndFieldValue(T entity) {
		StringBuilder sb = new StringBuilder();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			
			Boolean antColumn = field.isAnnotationPresent(Column.class);
			Boolean antUV = field.isAnnotationPresent(UniqueValue.class);
			Boolean fieldIsId = field.getName().equalsIgnoreCase("id");
			
			if (antColumn && !antUV && !fieldIsId) {
				Column columnAnnotation = field.getAnnotation(Column.class);
				String fieldDbName = columnAnnotation.dbName();
				String fieldGetValuemethod = columnAnnotation.getJavaName();
				try {
					Method methodGetValue = clazz.getDeclaredMethod(fieldGetValuemethod);
					E value = (E) methodGetValue.invoke(entity);
					if (value.getClass() == String.class)
						value = (E) ("'" + value + "'");
					sb.append(fieldDbName + " = " + value);
					sb.append(", ");
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					e.printStackTrace();
					throw new GetValuesException(e.getMessage());
				}
			}
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 2);
		}
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	private String getDeleteQuery(E value) {
		if (value.getClass() == String.class) value = (E) ("'" + value + "'");
		return "DELETE FROM " + getTableName() + " WHERE " + getUniqueValueName() + " = " + value + ";";
	}
}
