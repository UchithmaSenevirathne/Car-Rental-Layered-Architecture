package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.DriverBO;
import lk.ijse.dao.custom.DriverDAO;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.dao.custom.impl.DriverDAOImpl;
import lk.ijse.dao.custom.impl.UserDAOImpl;
import lk.ijse.dto.DriverDto;
import lk.ijse.dto.DriverInTimeDto;
import lk.ijse.dto.UserDTO;
import lk.ijse.entity.Driver;
import lk.ijse.entity.DriverInTime;
import lk.ijse.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DriverBOImpl implements DriverBO {
    DriverDAO driverDAO = new DriverDAOImpl();
    UserDAO userDAO = new UserDAOImpl();
    @Override
    public List<DriverInTimeDto> gerDrInTime(String date) throws SQLException {
        List<DriverInTime> driverInTimes = driverDAO.gerDrInTime(date);
        List<DriverInTimeDto> dtoList = new ArrayList<>();

        for (DriverInTime driver : driverInTimes){
            dtoList.add(new DriverInTimeDto(driver.getName(),driver.getTime()));
        }
        return dtoList;
    }

    @Override
    public boolean updateUser(UserDTO dto) throws SQLException {
        return userDAO.update(new User(dto.getUserName(), dto.getPassword(), dto.getEmail(), dto.getRole()));
    }

    @Override
    public boolean updateDriver(DriverDto dto) throws SQLException {
        return driverDAO.update(new Driver(dto.getId(), dto.getName(), dto.getAddress(), dto.getEmail(), dto.getContact(), dto.getLicenseNo(), dto.getUserName(), dto.getAvailability()));
    }

    @Override
    public boolean saveUser(UserDTO dto) throws SQLException {
        return userDAO.save(new User(dto.getUserName(), dto.getPassword(), dto.getEmail(), dto.getRole()));
    }

    @Override
    public boolean saveDriver(DriverDto dto) throws SQLException {
        return driverDAO.save(new Driver(dto.getId(), dto.getName(), dto.getAddress(), dto.getEmail(), dto.getContact(), dto.getLicenseNo(), dto.getUserName(), dto.getAvailability()));
    }

    @Override
    public List<DriverDto> getAllDrivers() throws SQLException {
        List<Driver> drivers = driverDAO.getAll();
        List<DriverDto> dtoList = new ArrayList<>();

        for (Driver driver : drivers){
            dtoList.add(new DriverDto(driver.getId(), driver.getName(), driver.getAddress(), driver.getEmail(), driver.getContact(), driver.getLicenseNo(), driver.getUserName(), driver.getAvailability()));
        }
        return dtoList;
    }

    @Override
    public boolean deleteDriver(String userName) throws SQLException {
        return driverDAO.delete(userName);
    }

    @Override
    public boolean deleteUser(String userName) throws SQLException {
        return userDAO.delete(userName);
    }

    @Override
    public String getPassword(String userName) throws SQLException {
        return userDAO.getPassword(userName);
    }

    @Override
    public String generateNextId() throws SQLException {
        return driverDAO.generateNextId();
    }
}
