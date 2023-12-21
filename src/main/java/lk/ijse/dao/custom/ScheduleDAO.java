package lk.ijse.dao.custom;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.ScheduleDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ScheduleDAO {
    public List<ScheduleDTO> getSchedule(String userName) throws SQLException;
    public String getDrName(String userName) throws SQLException;
}
