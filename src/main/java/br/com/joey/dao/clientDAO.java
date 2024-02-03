package br.com.joey.dao;

import br.com.joey.dao.generic.GenericDAO;
import br.com.joey.domain.Cliente;

public class clientDAO extends GenericDAO<Cliente, Long> implements IClientDAO {
	
	@Override
	public Class<Cliente> getTipoClasse() {
		return Cliente.class;
	}
	
//	@Override
//	public Boolean insert(Cliente client) {
//		Connection connection = null;
//		PreparedStatement stm = null;
//		ResultSet rs = null;
//		try {
//			connection = ConnectionJdbcFactory.getConnection();
//			connection.setAutoCommit(false);
//			stm = connection.prepareStatement(getInsertSql(), Statement.RETURN_GENERATED_KEYS);
//			addInsertProps(stm, client);
//			stm.executeUpdate();
//			connection.commit();
//			rs = stm.getGeneratedKeys();
//			return rs.next();
//		} catch (SQLException e) {
//			rollBack(e, connection);
//		} finally {
//			ConnectionJdbcFactory.closeConnection(connection, stm, rs);
//		}
//		return null;
//	}
//	
//	private String getInsertSql() {
//		StringBuilder sb = new StringBuilder();
//		sb.append("INSERT INTO tb_cliente ");
//		sb.append("(id, cpf, nome, tel, endereco, numero, cidade, estado) ");
//		sb.append("VALUES (nextval('sq_cliente'), ?, ?, ?, ?, ?, ?, ?);");
//		return sb.toString();
//	}
//	
//	private void addInsertProps(PreparedStatement st, Cliente c) throws SQLException {
//		st.setLong(1, c.getCpf());
//		st.setString(2, c.getName());
//		st.setLong(3, c.getCel());
//		st.setString(4, c.getEnd());
//		st.setInt(5, c.getNumero());
//		st.setString(6, c.getCidade());
//		st.setString(7, c.getEstado());
//	}
//	
//	@Override
//	public Cliente select(Long cpf) {
//		Connection connection = null;
//		PreparedStatement stm = null;
//		ResultSet rs = null;
//		Cliente client = null;
//		try {
//			connection = ConnectionJdbcFactory.getConnection();
//			stm = connection.prepareStatement(getSelectSql());
//			addSelectProps(stm, cpf);
//			rs = stm.executeQuery();
//			
//			if (rs.next()) client = new Cliente(rs.getLong("id") ,rs.getString("nome"), rs.getLong("tel"),
//												rs.getLong("cpf"), rs.getString("endereco"), rs.getInt("numero"),
//												rs.getString("cidade"), rs.getString("estado"));				
//			return client;
//		} catch (SQLException e) {
//			rollBack(e, connection);
//		} finally {
//			ConnectionJdbcFactory.closeConnection(connection, stm, rs);
//		}
//		return client;
//	}
//	
//	private String getSelectSql() {
//		StringBuilder sb = new StringBuilder();
//		sb.append("SELECT * FROM tb_cliente ");
//		sb.append("WHERE cpf = ?;");
//		return sb.toString();
//	}
//
//	private void addSelectProps(PreparedStatement st, Long cpf) throws SQLException {
//		st.setLong(1, cpf);
//	}
//	
//	@Override
//	public Collection<Cliente> selectAll() {
//		Connection connection = null;
//		PreparedStatement stm = null;
//		ResultSet rs = null;
//		Cliente client = null;
//		try {
//			connection = ConnectionJdbcFactory.getConnection();
//			List<Cliente> list = new ArrayList<>();
//			stm = connection.prepareStatement(getSelectAll());
//			rs = stm.executeQuery();
//			
//			while (rs.next()) {
//				client = new Cliente(rs.getString("nome"), rs.getLong("tel"), rs.getLong("cpf"),
//						rs.getString("endereco"), rs.getInt("numero"),
//						rs.getString("cidade"), rs.getString("estado"));
//				list.add(client);
//			}
//			
//			return list;
//		} catch(SQLException e) {
//			rollBack(e, connection);
//		} finally {
//			ConnectionJdbcFactory.closeConnection(connection, stm, rs);
//		}
//		return null;
//	}
//
//	private String getSelectAll() {
//		return "SELECT * FROM tb_cliente;";
//	}
//
//	@Override
//	public Boolean update(Cliente newDataClient) {
//		Connection connection = null;
//		PreparedStatement stm = null;
//		try {
//			connection = ConnectionJdbcFactory.getConnection();
//			stm = connection.prepareStatement(getUpdateSql());
//			addUpdateProps(stm, newDataClient, newDataClient.getCpf());
//			int rowsAffected = stm.executeUpdate();
//			if (rowsAffected > 1) {
//				throw new DbException("Foi atualizado mais de um dado!");
//			}
//			if (rowsAffected < 1) {
//				throw new DbException("Não foi atualizado nenhum dado!");
//			}
//			return rowsAffected > 0 ? true : false;
//		} catch (SQLException e) {
//			rollBack(e, connection);
//		} finally {
//			ConnectionJdbcFactory.closeConnection(connection, stm, null); 
//		}
//		return null;
//	}
//	
//	private String getUpdateSql() {
//		StringBuilder sb = new StringBuilder();
//		sb.append("UPDATE tb_cliente ");
//		sb.append("SET nome=?, tel=?, endereco=?, numero=?, cidade=?, estado=? ");
//		sb.append("WHERE cpf = ?");
//		return sb.toString();
//	}
//
//	private void addUpdateProps(PreparedStatement st, Cliente c, Long cpf) throws SQLException {
//		st.setString(1, c.getName());
//		st.setLong(2, c.getCel());
//		st.setString(3, c.getEnd());
//		st.setInt(4, c.getNumero());
//		st.setString(5, c.getCidade());
//		st.setString(6, c.getEstado());
//		st.setLong(7, cpf);
//	}
//
//	@Override
//	public Boolean delete(Long cpf) { 
//		Connection connection = null;
//		PreparedStatement stm = null;
//		try {
//			connection = ConnectionJdbcFactory.getConnection();
//			stm = connection.prepareStatement(getDeleteSql());
//			addDeleteProps(stm, cpf);
//			int rowsAffected = stm.executeUpdate();
//			if (rowsAffected > 1) {
//				throw new DbException("Foi excluido mais de um dado!");
//			}
//			return rowsAffected > 0 ? true : false;
//		} catch (SQLException e) {
//			rollBack(e, connection);
//		} finally {
//			ConnectionJdbcFactory.closeConnection(connection, stm, null);
//		}
//		return null;
//	}
//
//	private String getDeleteSql() {
//		return "DELETE FROM tb_cliente WHERE cpf = ?;";
//	}
//	
//	private void addDeleteProps(PreparedStatement st, Long cpf) throws SQLException {
//		st.setLong(1, cpf);
//	}
//	
//	private Exception rollBack(Exception e, Connection con) {
//		if (e != null || con != null) {
//			try {
//				con.rollback();
//				throw new DbException(e.getMessage());
//			} catch (SQLException er) {
//				throw new DbException("Error trying to go back: " + er.getMessage());
//			}
//		}
//		return null;
//	}
}
