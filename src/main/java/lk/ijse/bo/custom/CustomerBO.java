package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.CustomerDto;
import lk.ijse.entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBO extends SuperBO {
    public boolean updateCustomer(CustomerDto dto) throws SQLException;
    public boolean saveCustomer(CustomerDto dto) throws SQLException;
    public List<CustomerDto> getAllCustomers() throws SQLException;
    public boolean deleteCustomer(String id) throws SQLException;
    public String generateNextId() throws SQLException;
    public int getCountCus() throws SQLException;
}
