package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.CustomerDto;
import java.sql.SQLException;
import java.util.List;

public interface CustomerBO extends SuperBO {
    boolean updateCustomer(CustomerDto dto) throws SQLException;
    boolean saveCustomer(CustomerDto dto) throws SQLException;
    List<CustomerDto> getAllCustomers() throws SQLException;
    boolean deleteCustomer(String id) throws SQLException;
    String generateNextId() throws SQLException;
    int getCountCus() throws SQLException;
}
