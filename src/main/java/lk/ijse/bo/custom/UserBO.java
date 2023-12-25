package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.ScheduleDTO;
import lk.ijse.dto.UserDTO;
import lk.ijse.entity.Schedule;
import lk.ijse.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserBO extends SuperBO {
    public boolean updateAdmin(UserDTO dto) throws SQLException;
    public boolean saveAdmin(UserDTO dto) throws SQLException;
    public String getPassword(String userName) throws SQLException;
    public List<UserDTO> getAllAdmins() throws SQLException;
    public boolean deleteAdmin(String userName) throws SQLException;
    public boolean isSuperAdm(String password) throws SQLException;
    public boolean changePwd(String confirmPwd, String userName) throws SQLException;
    public boolean checkUserName(String userName) throws SQLException;
    public String getEmail(String userName) throws SQLException;
    public boolean searchUser(String userName, String password) throws SQLException;
    public boolean checkAdmin(String userName, String password) throws SQLException;
    public boolean saveLogin(String logId, String userName, String date, String time) throws SQLException;
    public String generateNextId() throws SQLException;
    public List<ScheduleDTO> getSchedule(String userName) throws SQLException;
}
