package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.OneCarPayDAO;
import lk.ijse.entity.OneCarPay;
import java.sql.SQLException;
import java.util.List;

public class OneCarPayDAOImpl implements OneCarPayDAO {
    @Override
    public boolean save(OneCarPay entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO oneCarPayment VALUES(?, ?, ?, ?, ?)",
                entity.getBId(),
                entity.getCarNo(),
                entity.getExtraKm(),
                entity.getDriverCost(),
                entity.getSubTotal()
        );
    }

    @Override
    public List<OneCarPay> getAll() throws SQLException {
        return null;
    }

    @Override
    public OneCarPay search(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean update(OneCarPay entity) throws SQLException {
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
