package pl.kkisiel.java;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AccountSqlDAO implements AccountDAO {

	public List<Account> getAccountsByName(String imie, String nazwisko) {
		DbConnection con = new DbConnection();
		ArrayList<Account> lista = new ArrayList<Account>();
		try {
			String query = "SELECT * FROM account WHERE firstName LIKE '" + imie + "' AND lastName LIKE '" + nazwisko  + "'";
			ResultSet rs = con.getStatement().executeQuery(query);
			while(rs.next()) {
				Account k = new Account();
				k.setFirstName(rs.getString("firstName"));
				k.setLastName(rs.getString("lastName"));
				k.setBirthDate(rs.getDate("birthDate"));
				k.setPeselNumber(rs.getString("peselNumber"));
				k.setAccountId(rs.getInt("accountId"));
				lista.add(k);
			}
		} catch (Exception ex) {
			System.out.println("Error (getAccountsByName): " + ex);
		}
		return lista;
	}
	public Account getAccountById(int id) {
		DbConnection con = new DbConnection();
		Account account = new Account();
		try {
			String query = "SELECT * FROM accounts WHERE accountId = " + id;
			ResultSet rs = con.getStatement().executeQuery(query);
			if (rs.next()) {
				account = new Account(rs.getString("firstName"), rs.getString("lastName"), rs.getDate("birthDate"), rs.getString("peselNumber"), 
					rs.getDouble("balance"), rs.getInt("accountId"));
			}
		} catch (Exception ex) {
			System.out.println("Error (getAccountById): " + ex);
		}
		return account;
	}
	public void insertAccount(Account k) {
		DbConnection con = new DbConnection();
		try {
			String query = "INSERT INTO accounts (firstName, lastName, birthDate, peselNumber, balance) VALUES ('";
			query += k.getFirstName() + "', '" + k.getLastName() + "', '" + new java.sql.Date(k.getBirthDate().getTime()) + 
					"', '" + k.getPeselNumber() + "', " + k.getBalance() + ")";
			con.getStatement().executeUpdate(query);
		} catch (Exception ex) {
			System.out.println("Error (insertAccount): " + ex);
		}
	}

}
