package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dto.CustomerDto;

import java.sql.SQLException;

public interface CustomerDAO extends CrudDAO<CustomerDto> {
    public int getCountCus() throws SQLException;
}
