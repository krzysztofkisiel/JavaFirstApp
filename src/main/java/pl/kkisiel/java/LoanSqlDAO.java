package pl.kkisiel.java;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LoanSqlDAO implements LoanDAO {
	public Loan getLoanByValues(Car s, Account k, Date startDate, Date endDate) {
		DbConnection con = new DbConnection();
		Loan w = new Loan(s, k, startDate, endDate);
		try {
			String query = "SELECT * FROM loans WHERE carId = " + s.getCarId() + " AND userId = " + k.getAccountId() + 
					" AND startDate = '" + String.valueOf(startDate) + "' AND endDate = '" + String.valueOf(endDate) + "'";
			ResultSet rs = con.getStatement().executeQuery(query);
			if (rs.next()) {
				w.setAmountDue(rs.getDouble("amountDue"));
				w.setLoanId(rs.getInt("loanId"));
				w.setIsReturned(rs.getBoolean("isReturned"));
			}
		} catch (Exception ex) {
			System.out.println("Error in 'getLoanByValues': " + ex);
		}
		return w;
	}
	public List<Loan> getLoansByAccount(Account account) {
		DbConnection con = new DbConnection();
		List<Loan> loans = new ArrayList<Loan>();
		int accountId = account.getAccountId();
		try {
			String query = "SELECT * FROM loans WHERE userId = " + accountId;
			ResultSet rs = con.getStatement().executeQuery(query);
			while(rs.next()) {
				int loanId = rs.getInt("loanId");
				int carId = rs.getInt("carId");
				Date startDate = rs.getDate("startDate");
				Date endDate = rs.getDate("endDate");
				Date returnDate = rs.getDate("returnDate");
				double amountDue = rs.getDouble("amountDue");
				boolean isReturned = rs.getBoolean("isReturned");
				Car car = new CarSqlDAO().getCarById(carId);
				Loan loan = new Loan(loanId, car, account, startDate, endDate, returnDate, amountDue, isReturned);
				loans.add(loan);
			}
		} catch (Exception ex) {
			System.out.println("Error (getLoansByAccount): " + ex);
		}	
		return loans;
	}
	public double getAmountByAccountId(int id) {
		DbConnection con = new DbConnection();
		double sum = 0;
		try {
			String query = "SELECT SUM(amountDue) AS 'sum' FROM loans WHERE userId = " + id;
			ResultSet rs = con.getStatement().executeQuery(query);
			while(rs.next()) {
				sum = rs.getDouble("sum");
			}
		} catch (Exception ex) {
			System.out.println("Error (getAmountByAccountId): " + ex);
		}
		return sum;
	}
	public int getCountLoansByAccountId(int id) {
		DbConnection con = new DbConnection();
		int count = 0;
		try {
			String query = "SELECT COUNT(loanId) AS 'count' FROM loans WHERE userId = " + id;
			ResultSet rs = con.getStatement().executeQuery(query);
			while(rs.next()) {
				count = rs.getInt("count");
			}
		} catch (Exception ex) {
			System.out.println("Error (getCountLoansByAccountId)" + ex);
		}
		return count;
	}
	public int getLoanId(Car s, Account k, Date startDate, Date endDate) {
		DbConnection con = new DbConnection();
		int wynik = -1;
		try {
			String query = "SELECT loanId FROM loans WHERE carId = " + s.getCarId() + " AND userId = " + k.getAccountId() + 
					" AND startDate = '" + String.valueOf(startDate) + "' AND endDate = '" + String.valueOf(endDate) + "'";
			ResultSet rs = con.getStatement().executeQuery(query);
			while(rs.next()) {
				wynik = rs.getInt("loanId");
			}
		} catch (Exception ex) {
			System.out.println("Error (getLoanId): " + ex);
		}
		return wynik;
	}
	public void insertLoan(Loan w) {
		DbConnection con = new DbConnection();
		Calendar c1 = Calendar.getInstance();
		c1.set(0,0,0);
		Date d1 = c1.getTime();
		java.sql.Date data1 = new java.sql.Date(d1.getTime());
		try {
			String query = "INSERT INTO loans (carId, userId, startDate, endDate, returnDate, amountDue, isReturned) VALUES (";
			query += w.getCar().getCarId() + ", " + w.getAccount().getAccountId() + ", '" + w.getStartDate() + "', '" + 
					 w.getEndDate() + "', '" + data1 + "', " + w.getAmountDue() + ", " + w.getIsReturned() + ")";
			con.getStatement().executeUpdate(query);
		} catch (Exception ex) {
			System.out.println("Error (insertLoan): " + ex);
		}
	}
}
