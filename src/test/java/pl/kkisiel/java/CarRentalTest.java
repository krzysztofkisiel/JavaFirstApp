package pl.kkisiel.java;

import junit.framework.TestCase;

public class CarRentalTest extends TestCase {
	public CarRentalTest(String name) {
		super(name);
	}

	public void testCountLoans() {
		int i = new LoanSqlDAO().getCountLoansByAccountId(1);
		assertNotNull(i);
	}

	public void testFordFocus() {
		Car c = new CarSqlDAO().getCarById(1);
		assertEquals(c.toString(), "Ford Focus, rok: 2000");
	}
	
	public void testKrzysztofKisiel() {
		Account a = new AccountSqlDAO().getAccountById(1);
		assertEquals(a.toString(), "Krzysztof Kisiel");
	}
	
	public void testAmount() {
		Car c = new CarSqlDAO().getCarById(1);
		double cash = c.getDailyCost();
		assertNotNull(cash);
	}
	
	public void testTest() {
		double amount = new LoanSqlDAO().getAmountByAccountId(1);
		assertNotNull(amount);
	}
}
