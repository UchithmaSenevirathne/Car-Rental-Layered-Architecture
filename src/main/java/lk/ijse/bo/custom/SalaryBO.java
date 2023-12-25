package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.DriverDto;
import lk.ijse.dto.SalaryDTO;
import lk.ijse.entity.Driver;
import lk.ijse.entity.Salary;

import java.sql.SQLException;
import java.util.List;

public interface SalaryBO extends SuperBO {
    public boolean saveSalary(SalaryDTO dto) throws SQLException;
    public List<DriverDto> getAllDrivers(String search) throws SQLException;
    public List<SalaryDTO> getAllSalary() throws SQLException;
}
