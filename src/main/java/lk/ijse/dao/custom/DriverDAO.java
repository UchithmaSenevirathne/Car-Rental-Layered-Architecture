package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dto.DriverDto;
import lk.ijse.dto.DriverInTimeDto;
import lk.ijse.entity.Driver;
import lk.ijse.entity.DriverInTime;

import java.sql.SQLException;
import java.util.List;

public interface DriverDAO extends CrudDAO<Driver> {
    boolean updateAvailable(String id) throws SQLException;
    boolean updateAvailableYes(String bId) throws SQLException;
    List<DriverInTime> gerDrInTime(String date) throws SQLException;
    List<Driver> getAllDrivers(String search) throws SQLException;
}
