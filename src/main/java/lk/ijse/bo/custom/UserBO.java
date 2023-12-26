package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.ScheduleDTO;
import lk.ijse.dto.UserDTO;
import java.sql.SQLException;
import java.util.List;

public interface UserBO extends SuperBO {
    boolean updateAdmin(UserDTO dto) throws SQLException;
    boolean saveAdmin(UserDTO dto) throws SQLException;
    String getPassword(String userName) throws SQLException;
    List<UserDTO> getAllAdmins() throws SQLException;
    boolean deleteAdmin(String userName) throws SQLException;
    boolean isSuperAdm(String password) throws SQLException;
    boolean changePwd(String confirmPwd, String userName) throws SQLException;
    boolean checkUserName(String userName) throws SQLException;
    String getEmail(String userName) throws SQLException;
    boolean searchUser(String userName, String password) throws SQLException;
    boolean checkAdmin(String userName, String password) throws SQLException;
    boolean saveLogin(String logId, String userName, String date, String time) throws SQLException;
    String generateNextId() throws SQLException;
    List<ScheduleDTO> getSchedule(String userName) throws SQLException;
}
