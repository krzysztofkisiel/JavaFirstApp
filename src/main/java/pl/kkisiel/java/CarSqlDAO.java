package pl.kkisiel.java;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CarSqlDAO implements CarDAO{
	public Car getCarById(int id) {
		DbConnection con = new DbConnection();
		//Connection connection = con.getConnection();
		Car car = new Car();
		try {
			String query = "SELECT * FROM cars WHERE carId = " + id;
			ResultSet rs = con.getStatement().executeQuery(query);
			if(rs.next()) {
				car = new Car(rs.getInt("carId"), rs.getString("producent"), rs.getString("model"), rs.getInt("year"), rs.getDouble("fuelConsumption"),
					rs.getDouble("dailyCost"), rs.getBoolean("isAvailable"));
			}
		} catch (Exception ex) {
			System.out.println("Error (getCarById):" + ex);
		}
		return car;
	}
	public List<Car> getSamochodByName(String producent, String model) {
		DbConnection con = new DbConnection();
		//Connection connection = con.getConnection();
		ArrayList<Car> lista = new ArrayList<Car>();
		try {
			String query = "SELECT * FROM cars WHERE producent LIKE '%" + producent + "%' AND model LIKE '%" + model + "%'";
			ResultSet rs = con.getStatement().executeQuery(query);
			while(rs.next()) {
				Car s = new Car();
				s.setProducent(rs.getString("producent"));
				s.setModel(rs.getString("model"));
				s.setYear(rs.getInt("year"));
				s.setFuelConsumption(rs.getDouble("fuelConsumption"));
				s.setDailyCost(rs.getDouble("dailyCost"));
				s.setIsAvailable(rs.getBoolean("isAvailable"));
				s.setCarId(rs.getInt("carId"));
				lista.add(s);
			}
		} catch (Exception ex) {
			System.out.println("Error (getSamochodByName): " + ex);
		}
		return lista;
	}
	public void insertCar(Car s) {
		DbConnection con = new DbConnection();
		try {
			String query = "INSERT INTO cars (producent, model, year, ";
			query += "fuelConsumption, dailyCost, isAvailable) VALUES ('" + s.getProducent() + "', '" + s.getModel() + "', " + 
					s.getYear() + ", " + s.getFuelConsumption() + ", " + s.getDailyCost() + ", " + s.getIsAvailable() + ")";
			con.getStatement().executeUpdate(query);
		} catch (Exception ex) {
			System.out.println("Error (insertCar): " + ex);
		}
	}
	
}
