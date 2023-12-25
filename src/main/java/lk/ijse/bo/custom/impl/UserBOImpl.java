package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.UserBO;
import lk.ijse.dao.custom.ScheduleDAO;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.dao.custom.impl.ScheduleDAOImpl;
import lk.ijse.dao.custom.impl.UserDAOImpl;
import lk.ijse.dto.ScheduleDTO;
import lk.ijse.dto.UserDTO;
import lk.ijse.entity.Schedule;
import lk.ijse.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserBOImpl implements UserBO {
    UserDAO userDAO = new UserDAOImpl();
    ScheduleDAO scheduleDAO = new ScheduleDAOImpl();
    @Override
    public boolean updateAdmin(UserDTO dto) throws SQLException {
        return userDAO.update(new User(dto.getUserName(),dto.getPassword(), dto.getEmail(), dto.getRole()));
    }

    @Override
    public boolean saveAdmin(UserDTO dto) throws SQLException {
        return userDAO.save(new User(dto.getUserName(), dto.getPassword(), dto.getEmail(), dto.getRole()));
    }

    @Override
    public String getPassword(String userName) throws SQLException {
        return userDAO.getPassword(userName);
    }

    @Override
    public List<UserDTO> getAllAdmins() throws SQLException {
        List<User> users = userDAO.getAllAdmins();
        List<UserDTO> userDTOS = new ArrayList<>();

        for (User user : users){
            userDTOS.add(new UserDTO(user.getUserName(), user.getPassword(), user.getEmail(), user.getRole()));
        }
        return userDTOS;
    }

    @Override
    public boolean deleteAdmin(String userName) throws SQLException {
        return userDAO.delete(userName);
    }

    @Override
    public boolean isSuperAdm(String password) throws SQLException {
        return userDAO.isSuperAdm(password);
    }

    @Override
    public boolean changePwd(String confirmPwd, String userName) throws SQLException {
        return userDAO.changePwd(confirmPwd,userName);
    }

    @Override
    public boolean checkUserName(String userName) throws SQLException {
        return userDAO.checkUserName(userName);
    }

    @Override
    public String getEmail(String userName) throws SQLException {
        return userDAO.getEmail(userName);
    }

    @Override
    public boolean searchUser(String userName, String password) throws SQLException {
        return userDAO.search(userName,password);
    }

    @Override
    public boolean checkAdmin(String userName, String password) throws SQLException {
        return userDAO.checkAdmin(userName,password);
    }

    @Override
    public boolean saveLogin(String logId, String userName, String date, String time) throws SQLException {
        return userDAO.saveLogin(logId,userName,date,time);
    }

    @Override
    public String generateNextId() throws SQLException {
        return userDAO.generateNextId();
    }

    @Override
    public List<ScheduleDTO> getSchedule(String userName) throws SQLException {
        List<Schedule> schedules = scheduleDAO.getSchedule(userName);
        List<ScheduleDTO> dtoList = new ArrayList<>();

        for (Schedule schedule : schedules){
            dtoList.add(new ScheduleDTO(schedule.getBId(),schedule.getName(),schedule.getBrand(),schedule.getAddress(),schedule.getContact(),schedule.getPickUpDate(),schedule.getDays()));
        }
        return dtoList;
    }
}
