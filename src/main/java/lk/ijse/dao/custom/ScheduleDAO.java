package lk.ijse.dao.custom;

import lk.ijse.dto.ScheduleDTO;

import java.sql.SQLException;
import java.util.List;

public interface ScheduleDAO {
    List<ScheduleDTO> getSchedule(String userName) throws SQLException;
    String getDrName(String userName) throws SQLException;
}
