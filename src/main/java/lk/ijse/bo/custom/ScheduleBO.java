package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import java.sql.SQLException;

public interface ScheduleBO extends SuperBO {
    String getDrName(String userName) throws SQLException;
}
