package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;

import java.sql.SQLException;

public interface ScheduleBO extends SuperBO {
    public String getDrName(String userName) throws SQLException;
}
