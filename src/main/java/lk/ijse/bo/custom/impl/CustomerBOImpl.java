package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.CustomerBO;
import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.dto.CustomerDto;
import lk.ijse.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = new CustomerDAOImpl();

    @Override
    public boolean updateCustomer(CustomerDto dto) throws SQLException {
        return customerDAO.update(new Customer(dto.getId(), dto.getName(), dto.getAddress(), dto.getEmail(), dto.getContact()));
    }

    @Override
    public boolean saveCustomer(CustomerDto dto) throws SQLException {
        return customerDAO.save(new Customer(dto.getId(), dto.getName(), dto.getAddress(), dto.getEmail(), dto.getContact()));
    }

    @Override
    public List<CustomerDto> getAllCustomers() throws SQLException {
        List<Customer> customers = customerDAO.getAll();
        List<CustomerDto> customerDtos = new ArrayList<>();

        for (Customer customer : customers){
            customerDtos.add(new CustomerDto(customer.getId(), customer.getName(), customer.getAddress(), customer.getEmail(), customer.getContact()));
        }
        return customerDtos;
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException {
        return customerDAO.delete(id);
    }

    @Override
    public String generateNextId() throws SQLException {
        return customerDAO.generateNextId();
    }

    @Override
    public int getCountCus() throws SQLException {
        return customerDAO.getCountCus();
    }
}
