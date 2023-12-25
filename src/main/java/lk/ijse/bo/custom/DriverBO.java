package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.DriverDto;
import lk.ijse.dto.DriverInTimeDto;
import lk.ijse.dto.UserDTO;
import lk.ijse.entity.Driver;
import lk.ijse.entity.DriverInTime;
import lk.ijse.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface DriverBO extends SuperBO {
    public List<DriverInTimeDto> gerDrInTime(String date) throws SQLException;
    public boolean updateUser(UserDTO dto) throws SQLException;
    public boolean updateDriver(DriverDto dto) throws SQLException;
    public boolean saveUser(UserDTO dto) throws SQLException;
    public boolean saveDriver(DriverDto dto) throws SQLException;
    public List<DriverDto> getAllDrivers() throws SQLException;
    public boolean deleteDriver(String userName) throws SQLException;
    public boolean deleteUser(String userName) throws SQLException;
    public String getPassword(String userName) throws SQLException;
    public String generateNextId() throws SQLException;
}
