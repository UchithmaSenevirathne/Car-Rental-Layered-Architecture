package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.DriverDto;
import lk.ijse.dto.DriverInTimeDto;
import lk.ijse.dto.UserDTO;
import java.sql.SQLException;
import java.util.List;

public interface DriverBO extends SuperBO {
    List<DriverInTimeDto> gerDrInTime(String date) throws SQLException;
    boolean updateUser(UserDTO dto) throws SQLException;
    boolean updateDriver(DriverDto dto) throws SQLException;
    boolean saveUser(UserDTO dto) throws SQLException;
    boolean saveDriver(DriverDto dto) throws SQLException;
    List<DriverDto> getAllDrivers() throws SQLException;
    boolean deleteDriver(String userName) throws SQLException;
    boolean deleteUser(String userName) throws SQLException;
    String getPassword(String userName) throws SQLException;
    String generateNextId() throws SQLException;
}
