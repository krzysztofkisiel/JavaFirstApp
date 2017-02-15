package pl.kkisiel.java;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainWindow extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private AddAccountDialog addAccountDialog;
	private AddCarDialog addCarDialog;
	private AddLoanDialog addLoanDialog;
	private ShowAccountDialog showAccountDialog;
	private ShowCarDialog showCarDialog;
	private ShowLoanDialog showLoanDialog;
	private JLabel labelAddAccount, labelAddCar, labelAddLoan;
	private JButton buttonAddAccount, buttonAddCar, buttonAddLoan, buttonShowAccounts, buttonShowCars, buttonShowLoans;
	private JMenuBar menuBar;
	private JMenu menuOptions, menuAdd, menuHelp, mOptionsColor;
	private JMenuItem mOptionsExit, mAddAccount, mAddCar, mAddLoan, mHelpAbout, mColorGrey, mColorWhite, mColorPink, mColorDefault;
	Color c;
	String title = "Car Rental 1.0";

	public MainWindow() {
		setTitle(title);
		setSize(500, 300);
		setLayout(null);
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		SwingUtilities.updateComponentTreeUI(this);
		
		menuBar = new JMenuBar();
		c = getBackground();

		menuOptions = new JMenu("Options");
		mOptionsColor = new JMenu("Change Background Color");
			mColorGrey = new JMenuItem("Grey..");
			mColorGrey.addActionListener(this);
			mColorWhite = new JMenuItem("White..");
			mColorWhite.addActionListener(this);
			mColorPink = new JMenuItem("Pink..");
			mColorPink.addActionListener(this);
			mColorDefault = new JMenuItem("Default..");
			mColorDefault.addActionListener(this);
			mOptionsColor.add(mColorGrey);
			mOptionsColor.add(mColorWhite);
			mOptionsColor.add(mColorPink);
			mOptionsColor.addSeparator();
			mOptionsColor.add(mColorDefault);
		mOptionsExit = new JMenuItem("Exit", 'X');
		mOptionsExit.addActionListener(this);
		
		menuOptions.add(mOptionsColor);
		menuOptions.addSeparator();
		menuOptions.add(mOptionsExit);
		
		menuAdd = new JMenu("Add..");
		mAddAccount = new JMenuItem("New Account");
		mAddAccount.addActionListener(this);
		mAddCar = new JMenuItem("New Car");
		mAddCar.addActionListener(this);
		mAddLoan = new JMenuItem("New Loan");
		mAddLoan.addActionListener(this);
		
		menuAdd.add(mAddAccount);
		menuAdd.add(mAddCar);
		menuAdd.add(mAddLoan);
		
		menuHelp = new JMenu("Help");
		mHelpAbout = new JMenuItem("About");
		mHelpAbout.addActionListener(this);
		
		menuHelp.add(mHelpAbout);
		
		menuBar.add(menuOptions);
		menuBar.add(menuAdd);
		menuBar.add(menuHelp);

		setJMenuBar(menuBar);
		
		buttonAddAccount = new JButton("Add Account");
		buttonAddAccount.setBounds(20, 30, 200, 30);
		add(buttonAddAccount);
		buttonAddAccount.addActionListener(this);
		
		labelAddAccount = new JLabel();
		labelAddAccount.setBounds(20, 60, 200, 20);
		add(labelAddAccount);
		
		buttonAddCar = new JButton("Add Car");
		buttonAddCar.setBounds(20, 90, 200, 30);
		add(buttonAddCar);
		buttonAddCar.addActionListener(this);
		
		labelAddCar = new JLabel();
		labelAddCar.setBounds(20, 120, 200, 20);
		add(labelAddCar);
		
		buttonAddLoan = new JButton("Add Loan");
		buttonAddLoan.setBounds(20, 150, 200, 30);
		add(buttonAddLoan);
		buttonAddLoan.addActionListener(this);
		
		labelAddLoan = new JLabel();
		labelAddLoan.setBounds(20, 180, 200, 20);
		add(labelAddLoan);
		
		buttonShowAccounts = new JButton("Show Accounts..");
		buttonShowAccounts.setBounds(250, 30, 200, 30);
		add(buttonShowAccounts);
		buttonShowAccounts.addActionListener(this);
		
		buttonShowCars = new JButton("Show Cars..");
		buttonShowCars.setBounds(250, 90, 200, 30);
		add(buttonShowCars);
		buttonShowCars.addActionListener(this);
		
		buttonShowLoans = new JButton("Show Loans..");
		buttonShowLoans.setBounds(250, 150, 200, 30);
		add(buttonShowLoans);
		buttonShowLoans.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if (source == buttonAddAccount || source == mAddAccount) {
			addAccountDialog = new AddAccountDialog(this);
			addAccountDialog.setVisible(true);
			addAccountDialog.setFocus();
			
			if (addAccountDialog.isConfirmed()) {
				try {
					Account account = new Account(addAccountDialog.getFirstName(), addAccountDialog.getLastName(), Date.valueOf(addAccountDialog.getBirthDate()), 
							addAccountDialog.getPeselNumber(), 0);
					new AccountSqlDAO().insertAccount(account);
					labelAddAccount.setForeground(Color.GREEN);
					labelAddAccount.setText("Account added successfully !");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null,  ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				labelAddAccount.setForeground(Color.RED);
				labelAddAccount.setText("Account not added !!");
			}
			labelAddAccount.setVisible(true);
			labelAddCar.setVisible(false);
			labelAddLoan.setVisible(false);
		} else if (source == buttonAddCar || source == mAddCar) {
			addCarDialog = new AddCarDialog(this);
			addCarDialog.setVisible(true);
			addCarDialog.setFocus();
			
			if (addCarDialog.isConfirmed()) {
				try {
					Car car = new Car(addCarDialog.getProducer(), addCarDialog.getModel(), Integer.parseInt(addCarDialog.getYear()), 
							Double.parseDouble(addCarDialog.getFuel()), Double.parseDouble(addCarDialog.getDailyCost()));
					new CarSqlDAO().insertCar(car);
					labelAddCar.setForeground(Color.GREEN);
					labelAddCar.setText("Car added succesfully !");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				labelAddCar.setForeground(Color.RED);
				labelAddCar.setText("Car not added !!");
			}
			labelAddCar.setVisible(true);
			labelAddAccount.setVisible(false);
			labelAddLoan.setVisible(false);
		} else if (source == buttonAddLoan || source == mAddLoan) {
			addLoanDialog = new AddLoanDialog(this);
			addLoanDialog.setVisible(true);
			addLoanDialog.setFocus();
			
			if (addLoanDialog.isConfirmed()) {
				try {
					Loan loan = new Loan(addLoanDialog.getCar(), addLoanDialog.getAccount(), addLoanDialog.getStartDate(), 
							addLoanDialog.getEndDate());
					/* Wywo³anie wzorca Metoda Szablonowa*/
					if (addLoanDialog.clientWantsDiscount()) {
						loan.giveDiscount();
					}
					new LoanSqlDAO().insertLoan(loan);
					loan = new LoanSqlDAO().getLoanByValues(addLoanDialog.getCar(), addLoanDialog.getAccount(), addLoanDialog.getStartDate(),
							addLoanDialog.getEndDate());
					/* Dwukrotne wywo³anie Dekoratora na obiekcie loan - wypisuje w konsoli DESCRIPTION DESCRIPTION*/
					loan = new CarDescription(loan);
					loan = new CarDescription(loan);
					System.out.println(loan.getDescription());
					labelAddLoan.setForeground(Color.GREEN);
					labelAddLoan.setText("Loan added successfully !");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				labelAddLoan.setForeground(Color.RED);
				labelAddLoan.setText("Loan not added !!");
			}
			labelAddLoan.setVisible(true);
			labelAddCar.setVisible(false);
			labelAddAccount.setVisible(false);	
		} else if (source == buttonShowAccounts) {
			showAccountDialog = new ShowAccountDialog(this);
			showAccountDialog.setVisible(true);
		} else if (source == buttonShowCars) {
			showCarDialog = new ShowCarDialog(this);
			showCarDialog.setVisible(true);
		} else if (source == buttonShowLoans) {
			showLoanDialog = new ShowLoanDialog(this);
			showLoanDialog.setVisible(true);
		} else if (source == mColorGrey) {
			getContentPane().setBackground(Color.GRAY);
		} else if (source == mColorWhite) {
			getContentPane().setBackground(Color.WHITE); 
		} else if (source == mColorPink) {
			getContentPane().setBackground(Color.PINK);
			repaint();
		} else if (source == mColorDefault) {
			getContentPane().setBackground(c);
		} else if (source == mOptionsExit) {
			dispose();
		} else if (source == mHelpAbout) {
			JOptionPane.showMessageDialog(null, "Application: " + title + "\nAuthor: Krzysztof Kisiel (64260)\nProgramowanie w jêzyku Java",
					"About", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}






