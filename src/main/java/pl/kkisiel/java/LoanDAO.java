package pl.kkisiel.java;

import java.util.Date;
import java.util.List;

public interface LoanDAO {
	public Loan getLoanByValues(Car s, Account k, Date startDate, Date endDate);
	public List<Loan> getLoansByAccount(Account account);
	public double getAmountByAccountId(int id);
	public int getCountLoansByAccountId(int id);
	public int getLoanId(Car s, Account k, Date startDate, Date endDate);
	public void insertLoan(Loan w);
}
