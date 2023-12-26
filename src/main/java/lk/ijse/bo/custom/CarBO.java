package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.CarDto;
import lk.ijse.dto.CarOutDto;
import java.sql.SQLException;
import java.util.List;

public interface CarBO extends SuperBO {
    boolean updateCar(CarDto dto) throws SQLException;
    boolean saveCar(CarDto dto) throws SQLException;
    List<CarDto> getAllCars() throws SQLException;
    boolean deleteCar(String carNo) throws SQLException;
    String generateNextId() throws SQLException;
    List<CarOutDto> getCarOut() throws SQLException;
}
