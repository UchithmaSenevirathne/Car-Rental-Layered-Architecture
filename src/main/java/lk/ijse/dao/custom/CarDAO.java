package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.CarDto;
import lk.ijse.dto.CarOutDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public interface CarDAO extends CrudDAO<CarDto> {
    public boolean updateAvailable(String id) throws SQLException;
    public boolean updateAvailableYes(String bId) throws SQLException;
    public List<CarOutDto> getCarOut() throws SQLException;
}
