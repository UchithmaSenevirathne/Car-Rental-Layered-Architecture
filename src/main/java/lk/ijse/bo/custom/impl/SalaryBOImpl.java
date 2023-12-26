package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.SalaryBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.DriverDAO;
import lk.ijse.dao.custom.SalaryDAO;
import lk.ijse.dto.DriverDto;
import lk.ijse.dto.SalaryDTO;
import lk.ijse.entity.Driver;
import lk.ijse.entity.Salary;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaryBOImpl implements SalaryBO {
    SalaryDAO salaryDAO = (SalaryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SALARY);
    DriverDAO driverDAO = (DriverDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.DRIVER);

    @Override
    public boolean saveSalary(SalaryDTO dto) throws SQLException {
        return salaryDAO.save(new Salary(dto.getDrSalId(), dto.getDrId(), dto.getAmount(), dto.getMonth()));
    }

    @Override
    public List<DriverDto> getAllDrivers(String search) throws SQLException {
        List<Driver> drivers = driverDAO.getAllDrivers(search);
        List<DriverDto> dtoList = new ArrayList<>();

        for (Driver driver : drivers){
            dtoList.add(new DriverDto(driver.getId(),driver.getName(),driver.getAddress(),driver.getEmail(), driver.getContact(), driver.getLicenseNo(), driver.getUserName(), driver.getAvailability()));
        }
        return dtoList;
    }

    @Override
    public List<SalaryDTO> getAllSalary() throws SQLException {
        List<Salary> salaries = salaryDAO.getAll();
        List<SalaryDTO> salaryDTOS = new ArrayList<>();

        for (Salary salary : salaries){
            salaryDTOS.add(new SalaryDTO(salary.getDrSalId(),salary.getDrId(),salary.getAmount(),salary.getMonth()));
        }
        return salaryDTOS;
    }
}
