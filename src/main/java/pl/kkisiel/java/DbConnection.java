package pl.kkisiel.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbConnection {
	private Connection con;
	private Statement st;
	private ResultSet rs;
	
	public Connection getConnection() {
		return this.con;
	}
	public Statement getStatement() {
		return this.st;
	}
	public ResultSet getResultSet() {
		return this.rs;
	}
	public void setStatement(Statement st) {
		this.st = st;
	}
	public void setResultSet(ResultSet rs) {
		this.rs = rs;
	}
	
	public DbConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			//con = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11155312?zeroDateTimeBehavior=convertToNull", "sql11155312", "e2y6CHLHgX");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/javadb01?zeroDateTimeBehavior=convertToNull&useSSL=false", "root", "");
			st = con.createStatement();
			
		} catch (Exception ex) {
			System.out.println("Error (DbConnection): " + ex);
		}
	}
	public ResultSet getTableData(String tabela) {
		try {
			String query = "SELECT * FROM " + tabela.toLowerCase();
			rs = st.executeQuery(query);		
		} catch (Exception ex) {
			System.out.println("Error (getTableData): " + ex);
		}
		return rs;
	}
	public void updateTableField(String tableName, String columnName, String newValue, int recordId) {
		try {
			String query = "UPDATE " + tableName + " SET " + columnName + " = '" + newValue + "' WHERE " + tableName.substring(0, tableName.length()-1) + "ID = " + recordId;
			st.executeUpdate(query);
		} catch (Exception ex) {
			System.out.println("Error (updateTableField): " + ex);
		}
	}
}
