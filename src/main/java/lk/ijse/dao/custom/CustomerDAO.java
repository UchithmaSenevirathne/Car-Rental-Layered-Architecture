package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dto.CustomerDto;

import java.sql.SQLException;

public interface CustomerDAO extends CrudDAO<CustomerDto> {
    int getCountCus() throws SQLException;
}
