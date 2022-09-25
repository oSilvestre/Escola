package Util;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class Conexao {

	public static Connection getConexao() {
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/meuDS");
			return ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Connection getConexao(boolean autoCommit) {
		Connection conn = getConexao();
		try {
			conn.setAutoCommit(autoCommit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}
