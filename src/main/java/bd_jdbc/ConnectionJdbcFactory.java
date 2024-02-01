package bd_jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import Exceptions.DbException;

public class ConnectionJdbcFactory {

	private static Connection connection;

	private ConnectionJdbcFactory() {
	};

	public static Connection getConnection() throws SQLException {
		if (connection == null || connection.isClosed()){
			connection = initConnection();
		}
		return connection;
	}

	private static Connection initConnection() {
			try {
				Properties props = loadProps();
				String dbUrl = props.getProperty("dburl");
				connection = DriverManager.getConnection(dbUrl, props);
				System.out.println("[:] Connected in : " + dbUrl);
				return connection;
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DbException(e.getMessage());
			}
	}

	private static Properties loadProps() {
		try (FileInputStream fs = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			props.load(fs);
			return props;

		} catch (IOException e) {
			e.printStackTrace();
			throw new DbException(e.getMessage());
		}
	}

	public static void closeConnection(Connection connection, PreparedStatement stm, ResultSet rs) {
		try {
			if ( connection != null && !connection.isClosed()) {
				connection.close();
				System.out.println("[:] Connection closed");
			}
			
			if (stm != null && !stm.isClosed()) stm.close();

			if (rs != null && !rs.isClosed()) rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
