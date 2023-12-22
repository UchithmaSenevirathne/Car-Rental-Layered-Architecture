package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dto.CarDto;
import lk.ijse.dto.CarOutDto;

import java.sql.SQLException;
import java.util.List;

public interface CarDAO extends CrudDAO<CarDto> {
    boolean updateAvailable(String id) throws SQLException;
    boolean updateAvailableYes(String bId) throws SQLException;
    List<CarOutDto> getCarOut() throws SQLException;
}
