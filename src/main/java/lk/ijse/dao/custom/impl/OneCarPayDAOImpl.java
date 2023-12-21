package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.OneCarPayDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.OneCarPayDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OneCarPayDAOImpl implements OneCarPayDAO {
    @Override
    public boolean save(OneCarPayDTO dto) throws SQLException {
        System.out.println("*****");
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO oneCarPayment VALUES(?, ?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getBId());
        pstm.setString(2, dto.getCarNo());
        pstm.setDouble(3, dto.getExtraKm());
        pstm.setDouble(4, dto.getDriverCost());
        pstm.setDouble(5, dto.getSubTotal());

        System.out.println(pstm.executeUpdate() > 0);
        return pstm.executeUpdate() > 0;
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
