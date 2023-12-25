package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.CarDto;
import lk.ijse.dto.CarOutDto;
import lk.ijse.entity.Car;
import lk.ijse.entity.CarOut;

import java.sql.SQLException;
import java.util.List;

public interface CarBO extends SuperBO {
    public boolean updateCar(CarDto dto) throws SQLException;
    public boolean saveCar(CarDto dto) throws SQLException;
    public List<CarDto> getAllCars() throws SQLException;
    public boolean deleteCar(String carNo) throws SQLException;
    public String generateNextId() throws SQLException;
    public List<CarOutDto> getCarOut() throws SQLException;
}
