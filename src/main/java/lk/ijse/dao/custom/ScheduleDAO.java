package lk.ijse.dao.custom;

import lk.ijse.dto.ScheduleDTO;
import lk.ijse.entity.Schedule;

import java.sql.SQLException;
import java.util.List;

public interface ScheduleDAO {
    List<Schedule> getSchedule(String userName) throws SQLException;
    String getDrName(String userName) throws SQLException;
}
