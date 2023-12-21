package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dto.UserDTO;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO extends CrudDAO<UserDTO> {
    public boolean search(String userName, String password) throws SQLException;
    public boolean saveLogin(String logId, String userName, String date, String time) throws SQLException;
    public List<UserDTO> getAllAdmins() throws SQLException;
    public String getPassword(String userName) throws SQLException;
    public boolean checkAdmin(String userName, String password) throws SQLException;
    public boolean isSuperAdm(String password) throws SQLException;
    public boolean checkUserName(String userName) throws SQLException;
    public String getEmail(String userName) throws SQLException;
    public boolean changePwd(String confirmPwd, String userName) throws SQLException;
}
