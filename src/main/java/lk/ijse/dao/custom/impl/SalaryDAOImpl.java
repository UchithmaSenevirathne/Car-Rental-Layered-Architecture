package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.SalaryDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.DriverDto;
import lk.ijse.dto.SalaryDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaryDAOImpl implements SalaryDAO {
    //private Connection connection;
    @Override
    public boolean save(SalaryDTO dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO driverSalary VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getDrSalId());
        pstm.setString(2, dto.getDrId());
        pstm.setDouble(3, dto.getAmount());
        pstm.setString(4, dto.getMonth());

        return pstm.executeUpdate() > 0;
    }
    @Override
    public List<SalaryDTO> getAll() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM driverSalary";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<SalaryDTO> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            String sal_Id = resultSet.getString(1);
            String dr_Id = resultSet.getString(2);
            double sal_amount = resultSet.getDouble(3);
            String sal_month = resultSet.getString(4);

            var dto = new SalaryDTO(sal_Id, dr_Id, sal_amount, sal_month);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public SalaryDTO search(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean update(SalaryDTO dto) throws SQLException {
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
