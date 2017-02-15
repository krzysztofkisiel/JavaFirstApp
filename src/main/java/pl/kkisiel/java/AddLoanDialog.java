package pl.kkisiel.java;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class AddLoanDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	JLabel lAccount, lCar, lStart, lEnd;
	JTextField tAccount, tCar, tStart, tEnd;
	JButton bConfirm, bCancel;
	boolean confirmData;
	JComboBox<Account> comboAccount;
	JComboBox<Car> comboCar;
	JCheckBox chboxDiscount;
	
	public AddLoanDialog(JFrame owner) {
		super(owner, "Add New Loan", true);
		setSize(450, 250);
		setLayout(null);
		setLocationRelativeTo(owner);

		lAccount = new JLabel("Select Account");
		lAccount.setBounds(20, 20, 150, 20);
		add(lAccount);
		comboAccount = new JComboBox<Account>();
		comboAccount.setBounds(180, 20, 150, 20);
		
		DbConnection con = new DbConnection();
		ResultSet accounts = con.getTableData("accounts");
		List<Account> accountList = new ArrayList<Account>();
		try {
			while(accounts.next()) {
				Account a = new Account();
				a.setAccountId(accounts.getInt("accountId"));
				a.setFirstName(accounts.getString("firstName"));
				a.setLastName(accounts.getString("lastName"));
				a.setBirthDate(accounts.getDate("birthDate"));
				a.setPeselNumber(accounts.getString("peselNumber"));
				a.setBalance(accounts.getDouble("balance"));
				
				accountList.add(a);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		for(int i = 0; i < accountList.size(); i++) {
			comboAccount.addItem(accountList.get(i));
		}
		add(comboAccount);
		
		lCar = new JLabel("Select Car");
		lCar.setBounds(20, 50, 130, 20);
		add(lCar);
		
		comboCar = new JComboBox<Car>();
		comboCar.setBounds(180, 50, 150, 20);
		
		ResultSet cars = con.getTableData("cars");
		List<Car> carList = new ArrayList<Car>();
		try {
			while(cars.next()) {
				Car c = new Car();
				c.setCarId(cars.getInt("carId"));
				c.setProducent(cars.getString("producent"));
				c.setModel(cars.getString("model"));
				c.setYear(cars.getInt("year"));
				c.setFuelConsumption(cars.getDouble("fuelConsumption"));
				c.setDailyCost(cars.getDouble("dailyCost"));
				c.setIsAvailable(cars.getBoolean("isAvailable"));
				if (cars.getBoolean("isAvailable") == true) {
					carList.add(c);
				}
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		for(int i = 0; i < carList.size(); i++) {
			comboCar.addItem(carList.get(i));
		}
		add(comboCar);
		
		lStart = new JLabel("Enter Start Date (yyyy-mm-dd)");
		lStart.setBounds(20, 80, 150, 20);
		add(lStart);
		
		tStart = new JTextField();
		tStart.setBounds(180, 80, 150, 20);
		add(tStart);
		
		lEnd = new JLabel("Enter End Date (yyyy-mm-dd)");
		lEnd.setBounds(20, 110, 150, 20);
		add(lEnd);
		
		tEnd = new JTextField();
		tEnd.setBounds(180, 110, 150, 20);
		add(tEnd);
		
		chboxDiscount = new JCheckBox("Discount");
		chboxDiscount.setBounds(20, 140, 120, 20);
		add(chboxDiscount);
		
		bConfirm = new JButton("Confirm");
		bConfirm.setBounds(20, 170, 120, 20);
		add(bConfirm);
		bConfirm.addActionListener(this);
		
		bCancel = new JButton("Cancel");
		bCancel.setBounds(150, 170, 120, 20);
		add(bCancel);
		bCancel.addActionListener(this);
		
	}

	public Car getCar() {
		return (Car) comboCar.getSelectedItem();
	}
	public Account getAccount() {
		return (Account) comboAccount.getSelectedItem();
	}
	public Date getStartDate() {
		return Date.valueOf(tStart.getText());
	}
	public Date getEndDate() {
		return Date.valueOf(tEnd.getText());
	}
	public void setFocus() {
		tStart.requestFocusInWindow();
	}
	/*Przekazanie wyboru do obs³ugi - Wzorzec Metoda Szablonowa*/
	public boolean clientWantsDiscount() {
		return chboxDiscount.isSelected();
	}
	public boolean isConfirmed() {
		return confirmData;
	}
	public void actionPerformed(ActionEvent e) {
		Object sender = e.getSource();
		
		if (sender == bConfirm) {
			try {
				Date a = Date.valueOf(tStart.getText());
				Date b = Date.valueOf(tEnd.getText());
				if (b.getTime() > a.getTime()) {
					confirmData = true;
					setVisible(false);
				} else {
					JOptionPane.showMessageDialog(null, "You cannot add End Date before Start Date", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Input date was not in correct format: yyyy-mm-dd", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
		} else if (sender == bCancel) {
			confirmData = false;
			setVisible(false);
		}
		
	}
	
}
