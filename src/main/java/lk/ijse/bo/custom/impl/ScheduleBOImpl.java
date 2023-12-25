package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.ScheduleBO;
import lk.ijse.dao.custom.ScheduleDAO;
import lk.ijse.dao.custom.impl.ScheduleDAOImpl;

import java.sql.SQLException;

public class ScheduleBOImpl implements ScheduleBO {
    ScheduleDAO scheduleDAO = new ScheduleDAOImpl();
    @Override
    public String getDrName(String userName) throws SQLException {
        return scheduleDAO.getDrName(userName);
    }
}
