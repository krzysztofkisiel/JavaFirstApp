package pl.kkisiel.java;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class AddAccountDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	JLabel lFirst, lLast, lBirth, lPesel;
	JTextField tFirst, tLast, tBirth, tPesel;
	JButton bConfirm, bCancel;
	boolean confirmData;
	
	public AddAccountDialog(JFrame owner) {
		
		super(owner, "Add New Account", true);
		setSize(400, 250);
		setLayout(null);
		setLocationRelativeTo(owner);
		
		lFirst = new JLabel("Enter firstname:");
		lFirst.setBounds(20, 20, 150, 20);
		add(lFirst);
		tFirst = new JTextField();
		tFirst.setBounds(180, 20, 150, 20);
		add(tFirst);
		
		lLast = new JLabel("Enter lastname:");
		lLast.setBounds(20, 50, 150, 20);
		add(lLast);
		tLast = new JTextField();
		tLast.setBounds(180, 50, 150, 20);
		add(tLast);
		
		lBirth = new JLabel("Enter birthdate (yyyy-mm-dd):");
		lBirth.setBounds(20, 80, 150, 20);
		add(lBirth);
		tBirth = new JTextField();
		tBirth.setBounds(180, 80, 150, 20);
		add(tBirth);
		
		lPesel = new JLabel("Enter pesel number:");
		lPesel.setBounds(20, 110, 150, 20);
		add(lPesel);
		tPesel = new JTextField();
		tPesel.setBounds(180, 110, 150, 20);
		add(tPesel);
		
		bConfirm = new JButton("Confirm");
		bConfirm.setBounds(20, 150, 120, 20);
		add(bConfirm);
		bConfirm.addActionListener(this);
		
		bCancel = new JButton("Cancel");
		bCancel.setBounds(150, 150, 120, 20);
		add(bCancel);
		bCancel.addActionListener(this);
		
	}
	
	public String getFirstName() {
		return tFirst.getText();
	}
	public String getLastName() {
		return tLast.getText();
	}
	public String getBirthDate() {
		return tBirth.getText();
	}
	public String getPeselNumber() {
		return tPesel.getText();
	}
	public boolean isConfirmed() {
		return confirmData;
	}
	public void setFocus() {
		tFirst.requestFocusInWindow();
	}
	
	public void actionPerformed(ActionEvent e) {
		Object sender = e.getSource();
		if (sender == bConfirm) {
			try {
				Date.valueOf(tBirth.getText());
				confirmData = true;
				setVisible(false);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Input date was not in correct format: yyyy-mm-dd", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
		} else if (sender == bCancel) {
			confirmData = false;
			setVisible(false);
		}
		
	}
	
}