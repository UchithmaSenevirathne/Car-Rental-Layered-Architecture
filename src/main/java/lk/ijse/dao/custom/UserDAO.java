package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dto.UserDTO;
import lk.ijse.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO extends CrudDAO<User> {
    boolean search(String userName, String password) throws SQLException;
    boolean saveLogin(String logId, String userName, String date, String time) throws SQLException;
    List<User> getAllAdmins() throws SQLException;
    String getPassword(String userName) throws SQLException;
    boolean checkAdmin(String userName, String password) throws SQLException;
    boolean isSuperAdm(String password) throws SQLException;
    boolean checkUserName(String userName) throws SQLException;
    String getEmail(String userName) throws SQLException;
    boolean changePwd(String confirmPwd, String userName) throws SQLException;
}
