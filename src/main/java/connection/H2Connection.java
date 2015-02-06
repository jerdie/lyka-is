package connection;
import java.sql.*;

import javax.swing.JOptionPane;

import org.h2.jdbcx.JdbcConnectionPool;

public class H2Connection {
	
	//TODO: Convert to file resource
	private static final String h2DBFile = "C:\\Users\\GeRarD\\workspace\\iams\\database\\lyka-is";
	public static JdbcConnectionPool h2ConnectionPool(){
		JdbcConnectionPool cp = JdbcConnectionPool.create("jdbc:h2:~/test", "sa","");
		return cp;
	}
	
	public static Connection h2DbConnection(){
		Connection conn = null;
		try {
			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection("jdbc:h2:"+h2DBFile, "sa", "");
			return conn;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
			return null;
		}
	        
	}
	
}
