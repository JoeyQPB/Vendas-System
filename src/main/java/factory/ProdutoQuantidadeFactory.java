package factory;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.joey.domain.Product;
import br.com.joey.domain.ProdutoQuantidade;

public class ProdutoQuantidadeFactory {
	public static ProdutoQuantidade convert(ResultSet rs) throws SQLException {
		Product prod = ProdutoFactory.convert(rs);
		ProdutoQuantidade prodQ = new ProdutoQuantidade();
		prodQ.setProduto(prod);
		prodQ.setId(rs.getLong("ID"));
		prodQ.setQuantidade(rs.getInt("QUANTIDADE"));
		prodQ.setValorTotal(rs.getDouble("VALOR_TOTAL"));
		return prodQ;
	}
}
