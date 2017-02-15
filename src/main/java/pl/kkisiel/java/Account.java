package pl.kkisiel.java;

import java.util.Date;


public class Account {
	int accountId;
	String firstName;
	String lastName;
	Date birthDate;
	String peselNumber;
	double balance = 0;
		
	public Account() {
	}
	public Account(String firstName, String lastName, Date birthDate, String peselNumber, double balance) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.peselNumber = peselNumber;
		this.balance = balance;
	}
	public Account(String firstName, String lastName, Date birthDate, String peselNumber, double balance, int accountId) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.peselNumber = peselNumber;
		this.balance = balance;
		this.accountId = accountId;
	}
	
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getPeselNumber() {
		return peselNumber;
	}
	public void setPeselNumber(String peselNumber) {
		this.peselNumber = peselNumber;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String toString() {
		return this.firstName + " " + this.lastName;
	}
}
