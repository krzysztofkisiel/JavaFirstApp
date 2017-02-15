package pl.kkisiel.java;

import pl.kkisiel.java.DbConnection;

public class Car {
	String producent;
	String model;
	int year;
	double fuelConsumption;
	double dailyCost;
	boolean isAvailable = true;
	int carId;
	
	public Car() {
	}
	public Car(String producent, String model, int year, double fuelConsumption, double dailyCost) {
		this.producent = producent;
		this.model = model;
		this.year = year;
		this.fuelConsumption = fuelConsumption;
		this.dailyCost = dailyCost;
	}
	public Car(int carId, String producent, String model, int year, double fuelConsumption, double dailyCost, boolean isAvailable) {
		this.carId = carId;
		this.producent = producent;
		this.model = model;
		this.year = year;
		this.fuelConsumption = fuelConsumption;
		this.dailyCost = dailyCost;
		this.isAvailable = isAvailable;
	}
	
	public String getProducent() {
		return this.producent;
	}
	public void setProducent(String producent) {
		this.producent = producent;
	}
	public String getModel() {
		return this.model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public int getYear() {
		return this.year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public double getFuelConsumption() {
		return this.fuelConsumption;
	}
	public void setFuelConsumption(double fuelConsumption) {
		this.fuelConsumption = fuelConsumption;
	}
	public double getDailyCost() {
		return this.dailyCost;
	}
	public void setDailyCost(double dailyCost) {
		this.dailyCost = dailyCost;
	}
	public boolean getIsAvailable() {
		return this.isAvailable;
	}
	public void setIsAvailable(boolean isAvailable) {
		DbConnection connection = new DbConnection();
		this.isAvailable = isAvailable;
		connection.updateTableField("cars", "isAvailable", String.valueOf(this.isAvailable?1:0), this.carId);
	}
	public int getCarId() {
		return this.carId;
	}
	public void setCarId(int carId) {
		this.carId = carId;
	}
	public String toString() {
		return this.producent + " " + this.model + ", rok: " + this.year;
	}
}