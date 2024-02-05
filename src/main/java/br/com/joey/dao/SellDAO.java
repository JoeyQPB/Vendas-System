package br.com.joey.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Exceptions.DbException;
import bd_jdbc.ConnectionJdbcFactory;
import br.com.joey.dao.generic.GenericDAO;
import br.com.joey.domain.Product;
import br.com.joey.domain.ProdutoQuantidade;
import br.com.joey.domain.Sell;
import br.com.joey.domain.Sell.Status;
import factory.ProdutoQuantidadeFactory;
import factory.VendaFactory;

public class SellDAO extends GenericDAO<Sell, String> implements ISellDAO{

	@Override
	public Class<Sell> getTipoClasse() {
		return Sell.class;
	}
	
	@Override
	public void atualiarDados(Sell entity, Sell entityCadastrado) {
		entityCadastrado.setCode(entity.getCode());
		entityCadastrado.setStatus(entity.getStatus());
	}
	
	@Override
	public Boolean insert(Sell entity) {
		Connection connection = null;
    	PreparedStatement stm = null;
    	try {
    		connection = ConnectionJdbcFactory.getConnection();
			stm = connection.prepareStatement(getQueryInsercao(), Statement.RETURN_GENERATED_KEYS);
			setParametrosQueryInsercao(stm, entity);
			int rowsAffected = stm.executeUpdate();

			if(rowsAffected > 0) {
				try (ResultSet rs = stm.getGeneratedKeys()){
					if (rs.next()) {
						entity.setId(rs.getLong(1));
					}
				}
				
				for (ProdutoQuantidade prod : entity.getProdutos()) {
					stm = connection.prepareStatement(getQueryInsercaoProdQuant());
					setParametrosQueryInsercaoProdQuant(stm, entity, prod);
					rowsAffected = stm.executeUpdate();
				}
				
				
				return true;
			}
			
		} catch (SQLException e) {
			throw new DbException("ERRO CADASTRANDO OBJETO ");
		} finally {
			ConnectionJdbcFactory.closeConnection(connection, stm, null);
		}
		return false;
	}
	
	private String getQueryInsercaoProdQuant() {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO TB_PRODUTO_QUANTIDADE ");
		sb.append("(ID, ID_PRODUTO_FK, ID_VENDA_FK, QUANTIDADE, VALOR_TOTAL)");
		sb.append("VALUES (nextval('sq_produto_quantidade'),?,?,?,?)");
		return sb.toString();
	}
	
	private void setParametrosQueryInsercaoProdQuant(PreparedStatement stm, Sell venda, ProdutoQuantidade prod) throws SQLException {
		stm.setLong(1, prod.getProduto().getId());
		stm.setLong(2, venda.getId());
		stm.setInt(3, prod.getQuantidade());
		stm.setDouble(4, prod.getValorTotal());
	}

	@Override
	public Boolean delete(String code) {
		throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITIDA");
	}
	
	@Override
	public void finalizarVenda(Sell venda) {
		
		Connection connection = null;
    	PreparedStatement stm = null;
    	try {
    		String sql = "UPDATE TB_VENDA SET STATUS_VENDA = ? WHERE ID = ?";
    		connection = ConnectionJdbcFactory.getConnection();
			stm = connection.prepareStatement(sql);
			stm.setString(1, Status.CONCLUIDA.name());
			stm.setLong(2, venda.getId());
			stm.executeUpdate();
			
			for (ProdutoQuantidade pq : venda.getProdutos()) {
				Product prod = pq.getProduto();
				Integer quantity = pq.getQuantidade();
				Integer quantityRest = prod.getQuantity() - quantity;

				String sql_update = "UPDATE INTO tb_produto set quantiade = " + quantityRest + " WHERE codigo = "
						+ prod.getCode() + ";";
				stm = connection.prepareStatement(sql_update);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException("ERRO ATUALIZANDO OBJETO ");
		} finally {
			ConnectionJdbcFactory.closeConnection(connection, stm, null);
		}
	}
	
	@Override
	public void cancelarVenda(Sell venda) {
		Connection connection = null;
    	PreparedStatement stm = null;
    	try {
    		String sql = "UPDATE TB_VENDA SET STATUS_VENDA = ? WHERE ID = ?";
    		connection = ConnectionJdbcFactory.getConnection();
			stm = connection.prepareStatement(sql);
			stm.setString(1, Status.CANCELADA.name());
			stm.setLong(2, venda.getId());
			stm.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException("ERRO ATUALIZANDO OBJETO ");
		} finally {
			ConnectionJdbcFactory.closeConnection(connection, stm, null);
		}
	}

	protected String getQueryInsercao() {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO TB_VENDA ");
		sb.append("(ID, CODIGO, ID_CLIENTE_FK, VALOR_TOTAL, DATA_VENDA, STATUS_VENDA)");
		sb.append("VALUES (nextval('sq_venda'),?,?,?,?,?)");
		return sb.toString();
	}

	protected void setParametrosQueryInsercao(PreparedStatement stmInsert, Sell entity) throws SQLException {
		stmInsert.setString(1, entity.getCode());
		stmInsert.setLong(2, entity.getCliente().getId());
		stmInsert.setDouble(3, entity.getTotal());
		stmInsert.setTimestamp(4, Timestamp.from(entity.getSellDate()));
		stmInsert.setString(5, entity.getStatus().name());
	}
	
	protected String getQueryExclusao() {
		throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITIDA");
	}

	protected void setParametrosQueryExclusao(PreparedStatement stmInsert, String valor) throws SQLException {
		throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITIDA");
	}

	protected String getQueryAtualizacao() {
		throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITIDA");
	}

	protected void setParametrosQueryAtualizacao(PreparedStatement stmUpdate, Sell entity) throws SQLException {
		throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITIDA");
	}

	protected void setParametrosQuerySelect(PreparedStatement stm, String valor) throws SQLException {
		stm.setString(1, valor);
	}
	
//	@Override
//	public Venda select(String valor) throws MaisDeUmRegistroException, TableException, DAOException {
//		//TODO pode ser feito desta forma
////		Venda venda = super.consultar(valor);
////		Cliente cliente = clienteDAO.consultar(venda.getCliente().getId());
////		venda.setCliente(cliente);
////		return venda;
//		
//		//TODO Ou pode ser feito com join
//		StringBuilder sb = sqlBaseSelect();
//		sb.append("WHERE V.CODIGO = ? ");
//		Connection connection = null;
//		PreparedStatement stm = null;
//		ResultSet rs = null;
//		try {
//    		//validarMaisDeUmRegistro();
//    		connection = getConnection();
//			stm = connection.prepareStatement(sb.toString());
//			setParametrosQuerySelect(stm, valor);
//			rs = stm.executeQuery();
//		    if (rs.next()) {
//		    	Venda venda = VendaFactory.convert(rs);
//		    	buscarAssociacaoVendaProdutos(connection, venda);
//		    	return venda;
//		    }		} catch (SQLException e) {
//				throw new DAOException("ERRO CONSULTANDO OBJETO ", e);
//			} finally {
//				closeConnection(connection, stm, rs);
//			}
//	    	return null;
//	}
	
	private void buscarAssociacaoVendaProdutos(Sell venda) {
		Connection conn = null;
		PreparedStatement stmProd = null;
		ResultSet rsProd = null;
		try {
			conn = ConnectionJdbcFactory.getConnection();
			StringBuilder sbProd = new StringBuilder();
		    sbProd.append("SELECT PQ.ID, PQ.QUANTIDADE, PQ.VALOR_TOTAL, ");
		    sbProd.append("P.ID AS ID_PRODUTO, P.CODIGO, P.NOME, P.DESCRICAO, P.VALOR ");
		    sbProd.append("FROM TB_PRODUTO_QUANTIDADE PQ ");
		    sbProd.append("INNER JOIN TB_PRODUTO P ON P.ID = PQ.ID_PRODUTO_FK ");
		    sbProd.append("WHERE PQ.ID_VENDA_FK = ?");
		    stmProd = conn.prepareStatement(sbProd.toString());
		    stmProd.setLong(1, venda.getId());
		    rsProd = stmProd.executeQuery();
		    Set<ProdutoQuantidade> produtos = new HashSet<>();
		    while(rsProd.next()) {
		    	ProdutoQuantidade prodQ = ProdutoQuantidadeFactory.convert(rsProd);
		    	produtos.add(prodQ);
		    }
		    venda.setProdutos(produtos);
		    venda.recalcularValorTotalVenda();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException("ERRO CONSULTANDO OBJETO ");
		} finally {
			ConnectionJdbcFactory.closeConnection(conn, stmProd, rsProd);
		}
	}
	
	@Override
	public void executeDelete(String sql){
		Connection connection = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
    		connection = ConnectionJdbcFactory.getConnection();
			stm = connection.prepareStatement(sql);
			stm.executeUpdate();
		    
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException("ERRO EXLUINDO OBJETO ");
		} finally {
			ConnectionJdbcFactory.closeConnection(connection, stm, rs);
		}
	}

	private void buscarAssociacaoVendaProdutos(Connection connection, Sell venda) {
		PreparedStatement stmProd = null;
		ResultSet rsProd = null;
		try {
			StringBuilder sbProd = new StringBuilder();
		    sbProd.append("SELECT PQ.ID, PQ.QUANTIDADE, PQ.VALOR_TOTAL, ");
		    sbProd.append("P.ID AS ID_PRODUTO, P.CODIGO, P.NOME, P.DESCRICAO, P.VALOR ");
		    sbProd.append("FROM TB_PRODUTO_QUANTIDADE PQ ");
		    sbProd.append("INNER JOIN TB_PRODUTO P ON P.ID = PQ.ID_PRODUTO_FK ");
		    sbProd.append("WHERE PQ.ID_VENDA_FK = ?");
		    stmProd = connection.prepareStatement(sbProd.toString());
		    stmProd.setLong(1, venda.getId());
		    rsProd = stmProd.executeQuery();
		    Set<ProdutoQuantidade> produtos = new HashSet<>();
		    while(rsProd.next()) {
		    	ProdutoQuantidade prodQ = ProdutoQuantidadeFactory.convert(rsProd);
		    	produtos.add(prodQ);
		    }
		    venda.setProdutos(produtos);
		    venda.recalcularValorTotalVenda();
		} catch (SQLException e) {
			throw new DbException("ERRO CONSULTANDO OBJETO ");
		} finally {
			ConnectionJdbcFactory.closeConnection(connection, stmProd, rsProd);
		}
	}
	
	@Override
	public Collection<Sell> selectAll(){
		List<Sell> lista = new ArrayList<>();
		StringBuilder sb = sqlBaseSelect();
		
		try {
    		Connection connection = ConnectionJdbcFactory.getConnection();
			PreparedStatement stm = connection.prepareStatement(sb.toString());
			ResultSet rs = stm.executeQuery();
		    while (rs.next()) {
		    	Sell venda = VendaFactory.convert(rs);
		    	buscarAssociacaoVendaProdutos(connection, venda);
		    	lista.add(venda);
		    }
		    
		} catch (SQLException e) {
			throw new DbException("ERRO CONSULTANDO OBJETO ");
		} 
    	return lista;
	}
	
	private StringBuilder sqlBaseSelect() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT V.ID AS ID_VENDA, V.CODIGO, V.VALOR_TOTAL, V.DATA_VENDA, V.STATUS_VENDA, ");
		sb.append("C.ID AS ID_CLIENTE, C.NOME, C.CPF, C.TEL, C.ENDERECO, C.NUMERO, C.CIDADE, C.ESTADO ");
		sb.append("FROM TB_VENDA V ");
		sb.append("INNER JOIN TB_CLIENTE C ON V.ID_CLIENTE_FK = C.ID ");
		return sb;
	}
}
