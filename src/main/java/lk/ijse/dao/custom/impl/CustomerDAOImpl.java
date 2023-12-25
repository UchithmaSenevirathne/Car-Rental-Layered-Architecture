package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.dto.CustomerDto;
import lk.ijse.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public boolean save(Customer entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO customer VALUES(?, ?, ?, ?, ?)",
                entity.getId(),
                entity.getName(),
                entity.getAddress(),
                entity.getEmail(),
                entity.getContact()
        );
    }
    @Override
    public List<Customer> getAll() throws SQLException {
        List<Customer> customers = new ArrayList<>();

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM customer");

        while (resultSet.next()){
            String cus_id = resultSet.getString(1);
            String cus_name = resultSet.getString(2);
            String cus_address = resultSet.getString(3);
            String cus_email = resultSet.getString(4);
            String cus_contact = resultSet.getString(5);

            var entity = new Customer(cus_id, cus_name, cus_address, cus_email, cus_contact);
            customers.add(entity);
        }
        return customers;
    }
    @Override
    public Customer search(String id) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM customer WHERE cusId = ?",
                id
        );

        Customer entity = null;

        if(resultSet.next()) {
            String cus_id = resultSet.getString(1);
            String cus_name = resultSet.getString(2);
            String cus_address = resultSet.getString(3);
            String cus_email = resultSet.getString(4);
            String cus_contact = resultSet.getString(5);

            entity = new Customer(cus_id, cus_name, cus_address, cus_email, cus_contact);
        }

        return entity;
    }
    @Override
    public boolean update(Customer entity) throws SQLException {
        return SQLUtil.execute("UPDATE customer SET name = ?, address = ?, email = ?, contact = ?  WHERE cusId = ?",
                entity.getName(),
                entity.getAddress(),
                entity.getEmail(),
                entity.getContact(),
                entity.getId()
        );
    }
    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM customer WHERE cusId = ?",
                id
        );
    }
    @Override
    public String generateNextId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT cusId FROM customer ORDER BY cusId DESC LIMIT 1");

        String currentCusId = null;

        if (resultSet.next()) {
            currentCusId = resultSet.getString(1);
            return splitCusId(currentCusId);
        }
        return splitCusId(null);
    }

    private String splitCusId(String currentCusId) {
        if (currentCusId != null) {
            String[] split = currentCusId.split("C");
            int id = Integer.parseInt(split[1]);
            id++;

            if(id==10){
                return "C0" + id;
            }else if(id == 100){
                return "C" + id;
            }
            return "C00" + id;
        }
        return "C001";
    }
    @Override
    public int getCountCus() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("select count(cusId) from customer");

        int count = 0;

        while (resultSet.next()){
            count = resultSet.getInt(1);
        }
        return count;
    }
}
