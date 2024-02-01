package br.com.joey.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import Exceptions.DbException;
import bd_jdbc.ConnectionJdbcFactory;
import br.com.joey.dao.generic.GenericDAO;
import br.com.joey.domain.Cliente;

public class clientDAO extends GenericDAO<Cliente, Long> implements IClientDAO {
	
	@Override
	public Class<Cliente> getTipoClasse() {
		return Cliente.class;
	}
	
	@Override
	public Boolean insert(Cliente client) {
		Connection connection = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			connection = ConnectionJdbcFactory.getConnection();
			stm = connection.prepareStatement(getInsertSql(), Statement.RETURN_GENERATED_KEYS);
			addInsertProps(stm, client);
			stm.executeUpdate();
			rs = stm.getGeneratedKeys();
			return rs.next();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			ConnectionJdbcFactory.closeConnection(connection, stm, rs);
		}
	}
	
	private String getInsertSql() {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO tb_cliente ");
		sb.append("(id, cpf, nome, tel, endereco, numero, cidade, estado) ");
		sb.append("VALUES (nextval('sq_cliente'), ?, ?, ?, ?, ?, ?, ?);");
		return sb.toString();
	}
	
	private void addInsertProps(PreparedStatement st, Cliente c) throws SQLException {
		st.setLong(1, c.getCpf());
		st.setString(2, c.getName());
		st.setLong(3, c.getCel());
		st.setString(4, c.getEnd());
		st.setInt(5, c.getNumero());
		st.setString(6, c.getCidade());
		st.setString(7, c.getEstado());
	}
	
	@Override
	public Cliente get(Long cpf) {
		Connection connection = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		Cliente client = null;
		try {
			connection = ConnectionJdbcFactory.getConnection();
			stm = connection.prepareStatement(getSelectSql());
			addSelectProps(stm, cpf);
			rs = stm.executeQuery();
			
			if (rs.next()) client = new Cliente(rs.getLong("id") ,rs.getString("nome"), rs.getLong("tel"),
												rs.getLong("cpf"), rs.getString("endereco"), rs.getInt("numero"),
												rs.getString("cidade"), rs.getString("estado"));				
			return client;
		} catch(SQLException e ) {
			e.printStackTrace();
			throw new DbException(e.getMessage());
		} finally {
			ConnectionJdbcFactory.closeConnection(connection, stm, rs);
		}
	}
	
	private String getSelectSql() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM tb_cliente ");
		sb.append("WHERE cpf = ?;");
		return sb.toString();
	}

	private void addSelectProps(PreparedStatement st, Long cpf) throws SQLException {
		st.setLong(1, cpf);
	}
	
	@Override
	public Collection<Cliente> getAll() {
		Connection connection = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		Cliente client = null;
		try {
			connection = ConnectionJdbcFactory.getConnection();
			List<Cliente> list = new ArrayList<>();
			stm = connection.prepareStatement(getSelectAll());
			rs = stm.executeQuery();
			
			while (rs.next()) {
				client = new Cliente(rs.getString("nome"), rs.getLong("tel"), rs.getLong("cpf"),
						rs.getString("endereco"), rs.getInt("numero"),
						rs.getString("cidade"), rs.getString("estado"));
				list.add(client);
			}
			
			return list;
		} catch(SQLException e) {
			e.printStackTrace();
			throw new DbException(e.getMessage());
		} finally {
			ConnectionJdbcFactory.closeConnection(connection, stm, rs);
		}
	}

	private String getSelectAll() {
		return "SELECT * FROM tb_cliente;";
	}

	@Override
	public Boolean update(Cliente newDataClient) {
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			connection = ConnectionJdbcFactory.getConnection();
			stm = connection.prepareStatement(getUpdateSql());
			addUpdateProps(stm, newDataClient, newDataClient.getCpf());
			int rowsAffected = stm.executeUpdate();
			if (rowsAffected > 1) {
				throw new DbException("Foi atualizado mais de um dado!");
			}
			if (rowsAffected < 1) {
				throw new DbException("Não foi atualizado nenhum dado!");
			}
			return rowsAffected > 0 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e.getMessage());
		} finally {
			ConnectionJdbcFactory.closeConnection(connection, stm, null);
		}
	}
	
	private String getUpdateSql() {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE tb_cliente ");
		sb.append("SET nome=?, tel=?, endereco=?, numero=?, cidade=?, estado=? ");
		sb.append("WHERE cpf = ?");
		return sb.toString();
	}

	private void addUpdateProps(PreparedStatement st, Cliente c, Long cpf) throws SQLException {
		st.setString(1, c.getName());
		st.setLong(2, c.getCel());
		st.setString(3, c.getEnd());
		st.setInt(4, c.getNumero());
		st.setString(5, c.getCidade());
		st.setString(6, c.getEstado());
		st.setLong(7, cpf);
	}

	@Override
	public Boolean delete(Long cpf) {
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			connection = ConnectionJdbcFactory.getConnection();
			stm = connection.prepareStatement(getDeleteSql());
			addDeleteProps(stm, cpf);
			int rowsAffected = stm.executeUpdate();
			if (rowsAffected > 1) {
				throw new DbException("Foi excluido mais de um dado!");
			}
			return rowsAffected > 0 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e.getMessage());
		} finally {
			ConnectionJdbcFactory.closeConnection(connection, stm, null);
		}
	}

	private String getDeleteSql() {
		return "DELETE FROM tb_cliente WHERE cpf = ?;";
	}
	
	private void addDeleteProps(PreparedStatement st, Long cpf) throws SQLException {
		st.setLong(1, cpf);
	}
}
