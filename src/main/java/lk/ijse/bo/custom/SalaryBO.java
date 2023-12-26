package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.DriverDto;
import lk.ijse.dto.SalaryDTO;
import java.sql.SQLException;
import java.util.List;

public interface SalaryBO extends SuperBO {
    boolean saveSalary(SalaryDTO dto) throws SQLException;
    List<DriverDto> getAllDrivers(String search) throws SQLException;
    List<SalaryDTO> getAllSalary() throws SQLException;
}
