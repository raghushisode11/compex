package autoFW.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.testng.Reporter;

public class DBUtilities {
	
	Connection con;

	public void connDB() throws ClassNotFoundException, SQLException {
		System.out.println("connecting..");
		String dbUrl = "jdbc:mysql://localhost:3306/seleniumdb";
		String username = "root";
		String password = "";
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection(dbUrl, username, password);
		System.out.println("connected");
	}

	public void disconnectDB() throws SQLException {
		con.close();
	}

	public String getConstantVal(String key_name) throws SQLException, ClassNotFoundException {
		connDB();
		Reporter.log("Fetching value of '" + key_name + "' from testtable table");
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select password from testtable where username='" + key_name + "'");
		String val = null;
		if(rs.next()) {
			 val = rs.getString(1);
		}
		disconnectDB();
		Reporter.log("Value of '" + key_name + "' from testtable table is: " + val);
		return val;
	}
	
	public List<String> getUserList() throws SQLException, ClassNotFoundException {
		Reporter.log("Fetching value of testtable from table");
		connDB();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select * from testtable ");
		String val = null;
		List<String> userList = new ArrayList<String>();
		while(rs.next()) {
			 val = rs.getString(1);
			 userList.add(val);
		}
		disconnectDB();

		return userList;
	}

}
