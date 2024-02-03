package br.com.joey.dao;

import br.com.joey.dao.generic.GenericDAO;
import br.com.joey.domain.Product;

public class ProductDAO extends GenericDAO<Product, String> implements IProductDAO{

	@Override
	public Class<Product> getTipoClasse() {
		return Product.class;
	}
	
//	public Boolean insert(Product product) {
//		Connection connection = null;
//		PreparedStatement stm = null;
//		ResultSet rs = null;
//		try {
//			connection = ConnectionJdbcFactory.getConnection();
//			stm = connection.prepareStatement(getInsertSQL(), Statement.RETURN_GENERATED_KEYS);
//			addInsertProps(stm, product);
//			stm.executeUpdate();
//			rs = stm.getGeneratedKeys();
//			return rs.next();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new DbException(e.getMessage());
//		} finally {
//			ConnectionJdbcFactory.closeConnection(connection, stm, rs);
//		}
//	}

//	private String getInsertSQL() {
//		StringBuilder sb = new StringBuilder();
//		sb.append("INSERT INTO tb_produto ");
//		sb.append("(id, code, nome, descricao, preco) ");
//		sb.append("VALUES (nextval('sq_produto'), ?, ?, ?, ?);");
//		return sb.toString();
//	}
	
//	private void addInsertProps(PreparedStatement stm, Product product) throws SQLException {
//		stm.setString(1, product.getCode());
//		stm.setString(2, product.getName());
//		stm.setString(3, product.getDescricao());
//		stm.setDouble(4, product.getPrice());
//	}
//
//
//	@Override
//	public Product select(String code) {
//		Connection connection = null;
//		PreparedStatement stm = null;
//		ResultSet rs = null;
//		Product product = null;
//		try {
//			connection = ConnectionJdbcFactory.getConnection();
//			stm = connection.prepareStatement(getSelectSQL());
//			addPropsSelect(stm, code);
//			rs = stm.executeQuery();
//			
//			if (rs.next()) product = new Product(rs.getLong("id"), rs.getString("code"),
//									 rs.getString("nome"), rs.getString("descricao"), rs.getDouble("preco"));
//			return product;		
//		} catch (SQLException e ) {
//			e.printStackTrace();
//			throw new DbException(e.getMessage());
//		} finally {
//			ConnectionJdbcFactory.closeConnection(connection, stm, rs);
//		}
//	}
//
//	private String getSelectSQL() {
//		StringBuilder sb = new StringBuilder();
//		sb.append("SELECT * FROM tb_produto ");
//		sb.append("WHERE code = ?;");
//		return sb.toString();
//	}
//
//	private void addPropsSelect(PreparedStatement stm, String code) throws SQLException {
//		stm.setString(1, code);
//	}
//	
//	@Override
//	public Collection<Product> selectAll() {
//		Connection connection = null;
//		PreparedStatement stm = null;
//		ResultSet rs = null;
//		Product product = null;
//		Collection<Product> list = new ArrayList<>();
//		try {
//			connection = ConnectionJdbcFactory.getConnection();
//			stm = connection.prepareStatement(getSelectAllSQL());
//			rs = stm.executeQuery();
//			
//			while (rs.next()) {
//				product = new Product(rs.getLong("id"), rs.getString("code"), rs.getString("nome"),
//						rs.getString("descricao"), rs.getDouble("preco"));
//				list.add(product);
//			}
//			return list;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new DbException(e.getMessage());
//		} finally {
//			ConnectionJdbcFactory.closeConnection(connection, stm, rs);
//		}
//	}
//	
//	private String getSelectAllSQL() {
//		return "SELECT * FROM tb_produto";
//	}
//	
//	@Override
//	public Boolean update(Product product) {
//		Connection connection = null;
//		PreparedStatement stm = null;
//		try {
//			connection = ConnectionJdbcFactory.getConnection();
//			stm = connection.prepareStatement(getUpdateSQL());
//			addPropsUpdate(stm, product);
//			int rowsAffected = stm.executeUpdate();
//			if (rowsAffected > 1) {
//				throw new DbException("Foi atualizado mais de um dado!");
//			}
//			if (rowsAffected < 1) {
//				throw new DbException("Não foi atualizado nenhum dado!");
//			}
//			return rowsAffected > 0 ? true : false;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new DbException(e.getMessage());
//		} finally {
//			ConnectionJdbcFactory.closeConnection(connection, stm, null);
//		}
//	}
//	
//	private String getUpdateSQL() {
//		StringBuilder sb = new StringBuilder();
//		sb.append("UPDATE tb_produto ");
//		sb.append("SET nome=?, descricao=?, preco=? ");
//		sb.append("WHERE code = ?");
//		return sb.toString();
//	}
//
//	private void addPropsUpdate(PreparedStatement stm, Product p) throws SQLException {
//		stm.setString(1, p.getName());
//		stm.setString(2, p.getDescricao());
//		stm.setDouble(3, p.getPrice());
//		stm.setString(4, p.getCode());
//	}
//
//	@Override
//	public Boolean delete(String code) {
//		Connection connection = null;
//		PreparedStatement stm = null;
//		try {
//			connection = ConnectionJdbcFactory.getConnection();
//			stm = connection.prepareStatement(getDeleteSQL());
//			addPropsDelete(stm, code);
//			int rowsAffected = stm.executeUpdate();
//			if (rowsAffected > 1) {
//				throw new DbException("Foi excluido mais de um dado!");
//			}
//			return rowsAffected > 0 ? true : false;
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new DbException(e.getMessage());
//		} finally {
//			ConnectionJdbcFactory.closeConnection(connection, stm, null);
//		}
//	}
//
//	private String getDeleteSQL() {
//		return "DELETE FROM tb_produto WHERE code = ?;";
//	}
//
//	private void addPropsDelete(PreparedStatement stm, String code) throws SQLException {
//		stm.setString(1, code);
//	}

}
