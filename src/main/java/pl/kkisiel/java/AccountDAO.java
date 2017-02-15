package pl.kkisiel.java;

import java.util.List;

public interface AccountDAO {
	public List<Account> getAccountsByName(String imie, String nazwisko);
	public Account getAccountById(int id);
	public void insertAccount(Account k);
}
