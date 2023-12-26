package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Customer;
import java.sql.SQLException;

public interface CustomerDAO extends CrudDAO<Customer> {
    int getCountCus() throws SQLException;
}
