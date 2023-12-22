package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.OneCarPayDAO;
import lk.ijse.dto.OneCarPayDTO;

import java.sql.SQLException;
import java.util.List;

public class OneCarPayDAOImpl implements OneCarPayDAO {
    @Override
    public boolean save(OneCarPayDTO dto) throws SQLException {
        return SQLUtil.execute("INSERT INTO oneCarPayment VALUES(?, ?, ?, ?, ?)",
                dto.getBId(),
                dto.getCarNo(),
                dto.getExtraKm(),
                dto.getDriverCost(),
                dto.getSubTotal()
        );
    }

    @Override
    public List<OneCarPayDTO> getAll() throws SQLException {
        return null;
    }

    @Override
    public OneCarPayDTO search(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean update(OneCarPayDTO dto) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public String generateNextId() throws SQLException {
        return null;
    }
}
