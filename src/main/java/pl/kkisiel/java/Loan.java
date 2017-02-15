package pl.kkisiel.java;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Loan {
	private Car car;
	private Account account;
	private Date startDate;
	private Date endDate;
	private Date returnDate;
	private double amountDue;
	private boolean isReturned = false;
	private int loanId;
	
	public Loan() {
	}
	public Loan(Car car, Account account, Date startDate, Date endDate) {
		this.car = car;
		this.account = account;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amountDue = car.getDailyCost() * Math.round((endDate.getTime() - startDate.getTime()) / 86400000.0);
		this.car.setIsAvailable(false);
	}
	public Loan(int loanId, Car car, Account account, Date startDate, Date endDate, Date returnDate, double amountDue, boolean isReturned) {
		this.loanId = loanId;
		this.car = car;
		this.account = account;
		this.startDate = startDate;
		this.endDate = endDate;
		this.returnDate = returnDate;
		this.amountDue = amountDue;
		this.isReturned = isReturned;
	}
	
	public void updateAmountDue(Car car, Date startDate, Date endDate)
	{
		this.amountDue = car.getDailyCost() * Math.round((endDate.getTime() - startDate.getTime()) / 86400000.0);
	}
	public Car getCar() {
		return this.car;
	}
	public void setCar(Car car) {
		if (this.car != car) {
			this.car = car;
			updateAmountDue(this.car, this.startDate, this.endDate);
		}
	}
	public Account getAccount() {
		return this.account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Date getStartDate() {
		return this.startDate;
	}
	public void setStartDate(Date startDate) {
		if (this.startDate != startDate) {
			this.startDate = startDate;
			updateAmountDue(this.car, this.startDate, this.endDate);
		}
	}
	public Date getEndDate() {
		return this.endDate;
	}
	public void setEndDate(Date endDate) {
		if (this.endDate != endDate) {
			this.endDate = endDate;
			updateAmountDue(this.car, this.startDate, this.endDate);
		}
	}
	public Date getReturnDate() {
		return this.returnDate;
	}
	public void carReturn(Date returnDate) {
		DbConnection connection = new DbConnection();
		try {
			if (returnDate.getTime() > endDate.getTime()) {
				this.amountDue = this.car.getDailyCost() * (Math.round((endDate.getTime() - startDate.getTime()) / 86400000.0)) + 
						1.5 * this.car.getDailyCost() * (Math.round((returnDate.getTime() - endDate.getTime()) / 86400000.0));
			} else if (returnDate.getTime() < endDate.getTime()) {
				this.amountDue = this.car.getDailyCost() * (Math.round((returnDate.getTime() - startDate.getTime()) / 86400000.));
			}
			this.isReturned = true;
			this.car.setIsAvailable(true);
			connection.updateTableField("loans", "returnDate", new SimpleDateFormat("yyyy-MM-dd").format(returnDate), this.loanId);
			connection.updateTableField("loans", "isReturned", String.valueOf((this.isReturned)?1:0), this.loanId);
			connection.updateTableField("loans", "amountDue", String.valueOf(this.amountDue), this.loanId);
		} catch (Exception ex) {
			System.out.println("Error in method 'zwrotSamochodu' (loanId = " + this.loanId + "): " + ex);
		}
	}
	public void setIsReturned(boolean isReturned) {
		this.isReturned = isReturned;
	}
	public double getAmountDue() {
		return this.amountDue;
	}
	public void giveDiscount() {
		setAmountDue(this.amountDue * 0.95);
	}
	public void setAmountDue(double amountDue) {
		this.amountDue = amountDue;
		DbConnection con = new DbConnection();
		con.updateTableField("loans", "amountDue", String.valueOf(amountDue), this.loanId);
	}
	public boolean getIsReturned() {
		return this.isReturned;
	}
	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}
	public int getLoanId() {
		return this.loanId;
	}
	public String getDescription() {
		return "";
	}
	public String toString() {
		return "Loan with Id: " + loanId + ":\n" +
				" - Car: " + this.car.toString() + "\n" +
				" - Account:   " + this.account.toString() + "\n" + 
				" - Start Date: " + this.startDate.toString() + "\n" +
				" - End Date:    " + this.endDate.toString() + "\n" + 
				" - Amount Due: " + this.amountDue;
	}
	
}
