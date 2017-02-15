package pl.kkisiel.java;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class ShowLoanDialog extends JDialog implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JLabel lListOfLoans;
	private JButton buttonReturn, buttonShowDetails, buttonOk;
	private JTable table;
	private List<Loan> loans = new ArrayList<Loan>();
	
	public ShowLoanDialog(JFrame owner) {
		super(owner, "Loans", true);
		setSize(650, 550);
		setLayout(null);
		setLocationRelativeTo(owner);
		
		lListOfLoans = new JLabel("List of Loans:");
		lListOfLoans.setBounds(25, 30, 150, 20);
		add(lListOfLoans);
		
		DbConnection con = new DbConnection();
		ResultSet rs = con.getTableData("loans");
		
		
		try {
			while(rs.next()) {
				int loanId = rs.getInt("loanId");
				Car car = new CarSqlDAO().getCarById(rs.getInt("carId"));
				Account account = new AccountSqlDAO().getAccountById(rs.getInt("userId"));
				Date startDate = rs.getDate("startDate");
				Date endDate = rs.getDate("endDate");
				Date returnDate = rs.getDate("returnDate");
				Double amountDue = rs.getDouble("amountDue");
				Boolean isReturned = rs.getBoolean("isReturned");
				
				Loan l = new Loan(loanId, car, account, startDate, endDate, returnDate, amountDue, isReturned);
				loans.add(l);
			}
			String[] colNames = {"Id", "Car", "Account", "Start Date", "End Date", "Return Date", "Amount Due", "Is returned"};
			Object[][] rowData = new Object[loans.size()][9];
			for(int i = 0; i < loans.size(); i++) {
				rowData[i][0] = Integer.valueOf(loans.get(i).getLoanId());
				rowData[i][1] = loans.get(i).getCar().toString();
				rowData[i][2] = loans.get(i).getAccount().toString();
				rowData[i][3] = loans.get(i).getStartDate().toString();
				rowData[i][4] = loans.get(i).getEndDate().toString();
				rowData[i][5] = loans.get(i).getReturnDate().toString();
				rowData[i][6] = loans.get(i).getAmountDue();
				rowData[i][7] = loans.get(i).getIsReturned()?"true":"false";
			}
			
			int size = 18*loans.size();
			if (size > 450)
				size = 450;
			
			table = new JTable(rowData, colNames);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setBounds(25, 50, 600, size);
			add(scrollPane);
			
			buttonReturn = new JButton("Return Car");
			buttonReturn.setBounds(20, size + 70, 125, 20);
			add(buttonReturn);
			buttonReturn.addActionListener(this);
			
			buttonShowDetails = new JButton("Show details:");
			buttonShowDetails.setBounds(500, size + 70, 125, 20);
			add(buttonShowDetails);
			buttonShowDetails.addActionListener(this);
			
			buttonOk = new JButton("OK");
			buttonOk.setBounds(500, size + 100, 125, 30);
			add(buttonOk);
			buttonOk.addActionListener(this);
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	

	public void actionPerformed(ActionEvent e) {
		Object sender = e.getSource();
		
		if (sender == buttonShowDetails) {
			try {
				int row = table.getSelectedRow();
				if (row != -1)
					JOptionPane.showMessageDialog(null, "You selected row number " + (row+1) + ".", "Row selection", JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(null, "Please select a loan..", "Error", JOptionPane.ERROR_MESSAGE);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Please select a loan..", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else if (sender == buttonOk) {
			setVisible(false);
		} else if (sender == buttonReturn) {			
			Calendar c1 = Calendar.getInstance();
			java.util.Date d1 = c1.getTime();
			try {
				int row = table.getSelectedRow();
				if (table.getValueAt(row, 7) != "true") {
					Loan thisLoan = loans.get(row);
					thisLoan.carReturn(d1);

					table.setValueAt("true", row, 7);
					table.setValueAt(thisLoan.getAmountDue(), row, 6);
					table.setValueAt(new SimpleDateFormat("yyyy-MM-dd").format(d1), row, 5);
				} else {
					JOptionPane.showMessageDialog(null, "This loan has ended.", "Info", JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Choose a loan..", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
