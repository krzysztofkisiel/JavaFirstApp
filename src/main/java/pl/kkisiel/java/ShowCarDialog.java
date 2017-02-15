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

public class ShowCarDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JLabel lListOfCars;
	private JButton buttonShowDetails, buttonOk;
	private JTable table;
	
	public ShowCarDialog(JFrame owner) {
		super(owner, "Cars", true);
		setSize(550, 450);
		setLayout(null);
		setLocationRelativeTo(owner);
		
		lListOfCars = new JLabel("List of Cars:");
		lListOfCars.setBounds(25, 30, 150, 20);
		add(lListOfCars);
		
		DbConnection con = new DbConnection();
		ResultSet rs = con.getTableData("cars");
		List<Car> cars = new ArrayList<Car>();
		try {
			while(rs.next()) {
				Car c = new Car();
				c.setCarId(rs.getInt("carId"));
				c.setProducent(rs.getString("producent"));
				c.setModel(rs.getString("model"));
				c.setYear(rs.getInt("year"));
				c.setFuelConsumption(rs.getDouble("fuelConsumption"));
				c.setDailyCost(rs.getDouble("dailyCost"));
				c.setIsAvailable(rs.getBoolean("isAvailable"));
				
				cars.add(c);
			}
			String[] colNames = {"Id", "Producer", "Model", "Year", "Fuel Consumption", "Daily Cost", "Available"};
			Object[][] rowData = new Object[cars.size()][7];
			for(int i = 0; i < cars.size(); i++) {
				rowData[i][0] = Integer.valueOf(cars.get(i).getCarId());
				rowData[i][1] = cars.get(i).getProducent();
				rowData[i][2] = cars.get(i).getModel();
				rowData[i][3] = Integer.valueOf(cars.get(i).getYear());
				rowData[i][4] = Double.valueOf(cars.get(i).getFuelConsumption());
				rowData[i][5] = Double.valueOf(cars.get(i).getDailyCost());
				rowData[i][6] = Boolean.valueOf(cars.get(i).getIsAvailable());
			}
			
			int size = 10*cars.size();
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
				if (row != -1)
					JOptionPane.showMessageDialog(null, "You selected row number " + (row+1) + ".", "Row selection", JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(null, "Please select a car..", "Error", JOptionPane.ERROR_MESSAGE);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Please select a car..", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else if (sender == buttonOk) {
			setVisible(false);
		}
		
	}

}
