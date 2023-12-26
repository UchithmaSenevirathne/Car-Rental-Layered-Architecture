package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.ScheduleBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.ScheduleDAO;
import java.sql.SQLException;

public class ScheduleBOImpl implements ScheduleBO {
    ScheduleDAO scheduleDAO = (ScheduleDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SCHEDULE);

    @Override
    public String getDrName(String userName) throws SQLException {
        return scheduleDAO.getDrName(userName);
    }
}
