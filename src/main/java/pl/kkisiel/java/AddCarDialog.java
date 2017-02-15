package pl.kkisiel.java;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class AddCarDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	JLabel lProducer, lModel, lYear, lFuel, lDailyCost;
	JTextField tProducer, tModel, tYear, tFuel, tDailyCost;
	JButton bConfirm, bCancel;
	boolean confirmData;
	
	public AddCarDialog(JFrame owner) {
		super(owner, "Add New Car", true);
		setSize(350, 250);
		setLayout(null);
		setLocationRelativeTo(owner);
		
		lProducer = new JLabel("Enter Car Producer:");
		lProducer.setBounds(20, 20, 120, 20);
		add(lProducer);	
		tProducer = new JTextField();
		tProducer.setBounds(150, 20, 150, 20);
		add(tProducer);
		
		lModel = new JLabel("Enter Car Model:");
		lModel.setBounds(20, 50, 120, 20);
		add(lModel);	
		tModel = new JTextField();
		tModel.setBounds(150, 50, 150, 20);
		add(tModel);
		
		lYear = new JLabel("Enter Car Year:");
		lYear.setBounds(20, 80, 120, 20);
		add(lYear);	
		tYear = new JTextField();
		tYear.setBounds(150, 80, 150, 20);
		add(tYear);
		
		lFuel = new JLabel("Enter Fuel Consumption:");
		lFuel.setBounds(20, 110, 120, 20);
		add(lFuel);	
		tFuel = new JTextField();
		tFuel.setBounds(150, 110, 150, 20);
		add(tFuel);
		
		lDailyCost = new JLabel("Enter Daily Cost:");
		lDailyCost.setBounds(20, 140, 120, 20);
		add(lDailyCost);	
		tDailyCost = new JTextField();
		tDailyCost.setBounds(150, 140, 150, 20);
		add(tDailyCost);
		
		bConfirm = new JButton("Confirm");
		bConfirm.setBounds(20, 180, 120, 20);
		add(bConfirm);
		bConfirm.addActionListener(this);
		
		bCancel = new JButton("Cancel");
		bCancel.setBounds(150, 180, 120, 20);
		add(bCancel);
		bCancel.addActionListener(this);
		
	}
	
	public String getProducer() {
		return tProducer.getText();
	}
	public String getModel() {
		return tModel.getText();
	}
	public String getYear() {
		return tYear.getText();
	}
	public String getFuel() {
		return tFuel.getText();
	}
	public String getDailyCost() {
		return tDailyCost.getText();
	}
	public boolean isConfirmed() {
		return confirmData;
	}
	public void setFocus() {
		tProducer.requestFocusInWindow();
	}

	public void actionPerformed(ActionEvent e) {
		Object sender = e.getSource();
		if (sender == bConfirm) {
			confirmData = true;
		} else if (sender == bCancel) {
			confirmData = false;
		}
		setVisible(false);
	}
}