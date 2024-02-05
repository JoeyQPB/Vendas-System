package factory;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.joey.domain.Product;

public class ProdutoFactory {
	public static Product convert(ResultSet rs) throws SQLException {
		Product prod = new Product();
		prod.setId(rs.getLong("ID_PRODUTO"));
		prod.setCode(rs.getString("CODIGO"));
		prod.setName(rs.getString("NOME"));
		prod.setDescricao(rs.getString("DESCRICAO"));
		prod.setPrice(rs.getDouble("VALOR"));
		return prod;
	}
}
