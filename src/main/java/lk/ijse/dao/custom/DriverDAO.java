package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dto.DriverDto;
import lk.ijse.dto.DriverInTimeDto;

import java.sql.SQLException;
import java.util.List;

public interface DriverDAO extends CrudDAO<DriverDto> {
    public boolean updateAvailable(String id) throws SQLException;
    public boolean updateAvailableYes(String bId) throws SQLException;
    public List<DriverInTimeDto> gerDrInTime(String date) throws SQLException;
    public List<DriverDto> getAllDrivers(String search) throws SQLException;
}
