package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.SalaryDAO;
import lk.ijse.entity.Salary;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaryDAOImpl implements SalaryDAO {
    @Override
    public boolean save(Salary entity) throws SQLException {

        return SQLUtil.execute("INSERT INTO driverSalary VALUES(?, ?, ?, ?)",
                entity.getDrSalId(),
                entity.getDrId(),
                entity.getAmount(),
                entity.getMonth()
        );
    }
    @Override
    public List<Salary> getAll() throws SQLException {
        List<Salary> salaries = new ArrayList<>();

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM driverSalary");

        while (resultSet.next()){
            String sal_Id = resultSet.getString(1);
            String dr_Id = resultSet.getString(2);
            double sal_amount = resultSet.getDouble(3);
            String sal_month = resultSet.getString(4);

            var entity = new Salary(sal_Id, dr_Id, sal_amount, sal_month);
            salaries.add(entity);
        }
        return salaries;
    }

    @Override
    public Salary search(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean update(Salary entity) throws SQLException {
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
