package pl.kkisiel.java;

/* Dekorator obiektu Loan */
public class CarDescription extends CarDecorator {
	Loan loan;
	public CarDescription(Loan _loan) {
		this.loan = _loan;
	}
	public String getDescription() {
		return loan.getDescription() + "DESCRIPTION ";
	}
}
