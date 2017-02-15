package pl.kkisiel.java;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class ShowAccountDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JLabel lListOfAccounts;
	private JButton buttonShowDetails, buttonOk;
	private JTable table;
	
	public ShowAccountDialog(JFrame owner) {
		
		super(owner, "Accounts", true);
		setSize(550, 450);
		setLayout(null);
		setLocationRelativeTo(owner);
		
		lListOfAccounts = new JLabel("List of Accounts:");
		lListOfAccounts.setBounds(25, 30, 150, 20);
		add(lListOfAccounts);
		
		DbConnection con = new DbConnection();
		ResultSet rs = con.getTableData("accounts");
		List<Account> accounts = new ArrayList<Account>();
		try {
			while(rs.next()) {
				Account a = new Account();
				a.setAccountId(rs.getInt("accountId"));
				a.setFirstName(rs.getString("firstName"));
				a.setLastName(rs.getString("lastName"));
				a.setBalance(rs.getDouble("balance"));
				a.setBirthDate(rs.getDate("birthDate"));
				a.setPeselNumber(rs.getString("peselNumber"));
				
				accounts.add(a);
			}
			String[] colNames = {"Id", "Firstname", "Lastname", "Birth Date", "PeselNumber", "Balance"};
			Object[][] rowData = new Object[accounts.size()][6];
			for(int i = 0; i < accounts.size(); i++) {
				rowData[i][0] = Integer.valueOf(accounts.get(i).getAccountId());
				rowData[i][1] = accounts.get(i).getFirstName();
				rowData[i][2] = accounts.get(i).getLastName();
				rowData[i][3] = String.valueOf(accounts.get(i).getBirthDate());
				rowData[i][4] = accounts.get(i).getPeselNumber();
				rowData[i][5] = String.valueOf(accounts.get(i).getBalance());
			}
			
			int size = 10*accounts.size();
			if (size > 150)
				size = 150;
			
			table = new JTable(rowData, colNames);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setBounds(25, 50, 500, size);
			add(scrollPane);
			
			buttonShowDetails = new JButton("Show details:");
			buttonShowDetails.setBounds(400, size + 70, 125, 20);
			add(buttonShowDetails);
			buttonShowDetails.addActionListener(this);
			
			buttonOk = new JButton("OK");
			buttonOk.setBounds(400, size + 100, 125, 30);
			add(buttonOk);
			buttonOk.addActionListener(this);
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object sender = e.getSource();
		
		if (sender == buttonShowDetails) {
			try {
				int row = table.getSelectedRow();

				int accountId = (int) table.getValueAt(row, 0);
				int countLoans = new LoanSqlDAO().getCountLoansByAccountId(accountId);
				double sumLoans = new LoanSqlDAO().getAmountByAccountId(accountId);

				JOptionPane.showMessageDialog(null, "Liczba wypozyczen: " + countLoans + "\n£¹czna kwota: " + sumLoans, "Liczba", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Please select account..", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else if (sender == buttonOk) {
			setVisible(false);
		}
	}
}
