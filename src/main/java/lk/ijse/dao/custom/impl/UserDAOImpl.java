package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.entity.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public boolean search(String userName, String password) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM user WHERE userName = ? AND password = ?",
                userName,
                password
        );

        if(resultSet.next()){
            return true;
        }
        return false;
    }

    @Override
    public List<User> getAll() throws SQLException {
        return null;
    }

    @Override
    public User search(String id) throws SQLException {
        return null;
    }
    @Override
    public String generateNextId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT logId FROM login ORDER BY logId DESC LIMIT 1");

        String currentLogId = null;

        if (resultSet.next()) {
            currentLogId = resultSet.getString(1);
            return splitLogId(currentLogId);
        }
        return splitLogId(null);
    }

    private static String splitLogId(String currentLogId) {
        if (currentLogId != null) {
            String[] split = currentLogId.split("L");
            int id = Integer.parseInt(split[1]);
            id++;
            if(id >= 10){
                return "L0" + id;
            }else if(id >= 100){
                return "L" + id;
            }
            return "L00" + id;
        }
        return "L001";
    }
    @Override
    public boolean saveLogin(String logId, String userName, String date, String time) throws SQLException {
        return SQLUtil.execute("INSERT INTO login VALUES(?, ?, ?, ?)",
                logId,
                userName,
                date,
                time
        );
    }
    @Override
    public List<User> getAllAdmins() throws SQLException {
        List<User> users = new ArrayList<>();

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM user where role = 'ADM'");

        while (resultSet.next()){
            String userName = resultSet.getString(1);
            String pwd = resultSet.getString(2);
            String email = resultSet.getString(3);
            String roll = resultSet.getString(4);

            var entity = new User(userName, pwd, email, roll);
            users.add(entity);
        }
        return users;
    }
    @Override
    public String getPassword(String userName) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT password FROM user WHERE userName = ?",
                userName
        );

        String password = null;

        while (resultSet.next()) {
            password = resultSet.getString(1);
        }
        return password;
    }
    @Override
    public boolean checkAdmin(String userName, String password) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM user where userName = ? and password = ? and role = 'ADM'",
                userName,
                password
        );

        while (resultSet.next()){
            String user_Name = resultSet.getString(1);
            String pwd = resultSet.getString(2);
            String email = resultSet.getString(3);
            String roll = resultSet.getString(4);

            var entity = new User(user_Name, pwd, email, roll);

            if(entity.equals(null)){
                return false;
            }
            return true;
        }
        return false;
    }
    @Override
    public boolean delete(String userName) throws SQLException {
        return SQLUtil.execute("DELETE FROM user WHERE userName = ?",
                userName
        );
    }
    @Override
    public boolean update(User entity) throws SQLException {
        return SQLUtil.execute("UPDATE user SET password = ?, email = ?, role = ? WHERE userName = ?",
                entity.getPassword(),
                entity.getEmail(),
                entity.getRole(),
                entity.getUserName()
        );
    }
    @Override
    public boolean save(User entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO user VALUES(?, ?, ?, ?)",
                entity.getUserName(),
                entity.getPassword(),
                entity.getEmail(),
                entity.getRole()
        );
    }
    @Override
    public boolean isSuperAdm(String password) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT password FROM user WHERE userName = 'Sadmin'");

        while (resultSet.next()) {
            String pwd = resultSet.getString(1);

            if(pwd.equals(password)){
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean checkUserName(String userName) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM user where userName = ?",
                userName
        );

        while (resultSet.next()){
            String user_Name = resultSet.getString(1);
            String pwd = resultSet.getString(2);
            String email = resultSet.getString(3);
            String roll = resultSet.getString(4);

            var entity = new User(user_Name, pwd, email, roll);

            System.out.println("dto :  "+entity);

            if(entity.equals(null)){
                return false;
            }
        }
        return true;
    }
    @Override
    public String getEmail(String userName) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT email FROM user WHERE userName = ?",
                userName
        );

        String email = null;

        while (resultSet.next()) {
            email = resultSet.getString(1);
        }
        return email;
    }
    @Override
    public boolean changePwd(String confirmPwd, String userName) throws SQLException {
        return SQLUtil.execute("UPDATE user SET password = ? WHERE userName = ?",
                confirmPwd,
                userName
        );
    }
}
