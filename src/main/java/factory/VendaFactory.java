package factory;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.joey.domain.Cliente;
import br.com.joey.domain.Sell;
import br.com.joey.domain.Sell.Status;

public class VendaFactory {
	
	public static Sell convert(ResultSet rs) throws SQLException{
		Cliente cliente = ClienteFactory.convert(rs);
		Sell venda = new Sell();
		venda.setCliente(cliente);
		venda.setId(rs.getLong("ID_VENDA"));
		venda.setCode(rs.getString("CODIGO"));
		venda.setTotal(rs.getDouble("VALOR_TOTAL"));
		venda.setSellDate(rs.getTimestamp("DATA_VENDA").toInstant());
		venda.setStatus(Status.getByName(rs.getString("STATUS_VENDA")));
		return venda;
	}

}
