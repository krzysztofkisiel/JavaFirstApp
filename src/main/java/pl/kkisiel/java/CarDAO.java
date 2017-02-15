package pl.kkisiel.java;

import java.util.List;

public interface CarDAO {
	public Car getCarById(int id);
	public List<Car> getSamochodByName(String producent, String model);
	public void insertCar(Car s);
}
