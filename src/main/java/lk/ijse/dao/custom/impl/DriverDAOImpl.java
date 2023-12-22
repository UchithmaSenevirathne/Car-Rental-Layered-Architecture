package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.DriverDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.DriverDto;
import lk.ijse.dto.DriverInTimeDto;
import lk.ijse.dto.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DriverDAOImpl implements DriverDAO {
    @Override
    public boolean save(DriverDto dto) throws SQLException {
        return SQLUtil.execute("INSERT INTO driver VALUES(?, ?, ?, ?, ?, ?, ?, ?)",
                dto.getId(),
                dto.getName(),
                dto.getAddress(),
                dto.getEmail(),
                dto.getContact(),
                dto.getLicenseNo(),
                dto.getUserName(),
                dto.getAvailability()
        );
    }
    @Override
    public List<DriverDto> getAll() throws SQLException {
        List<DriverDto> dtoList = new ArrayList<>();

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM driver");

        while (resultSet.next()){
            String dr_id = resultSet.getString(1);
            String dr_name = resultSet.getString(2);
            String dr_address = resultSet.getString(3);
            String dr_email = resultSet.getString(4);
            String dr_contact = resultSet.getString(5);
            String dr_licenseNo = resultSet.getString(6);
            String dr_userName = resultSet.getString(7);
            String dr_availability = resultSet.getString(8);

            var dto = new DriverDto(dr_id, dr_name, dr_address, dr_email, dr_contact, dr_licenseNo, dr_userName, dr_availability);
            dtoList.add(dto);
        }
        return dtoList;
    }
    @Override
    public boolean update(DriverDto driverDto) throws SQLException {
        return SQLUtil.execute("UPDATE driver SET name = ?, address = ?, email = ?, contact = ?, licenseNo = ?, userName = ?, availability = ? WHERE drId = ?",
                driverDto.getName(),
                driverDto.getAddress(),
                driverDto.getEmail(),
                driverDto.getContact(),
                driverDto.getLicenseNo(),
                driverDto.getUserName(),
                driverDto.getAddress(),
                driverDto.getId()
        );
    }
    @Override
    public DriverDto search(String id) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM driver WHERE drId = ?",
                id
        );

        DriverDto dto = null;

        if (resultSet.next()) {
            String dr_id = resultSet.getString(1);
            String dr_name = resultSet.getString(2);
            String dr_address = resultSet.getString(3);
            String dr_email = resultSet.getString(4);
            String dr_contact = resultSet.getString(5);
            String dr_licenseNo = resultSet.getString(6);
            String dr_userName = resultSet.getString(7);
            String dr_availability = resultSet.getString(8);

            dto = new DriverDto(dr_id, dr_name, dr_address, dr_email, dr_contact, dr_licenseNo, dr_userName, dr_availability);
        }
        return dto;
    }
    @Override
    public boolean delete(String userName) throws SQLException {
        return SQLUtil.execute("DELETE FROM driver WHERE userName = ?",
                userName
        );
    }
    @Override
    public boolean updateAvailable(String driverID) throws SQLException {
        return SQLUtil.execute("UPDATE driver SET availability = 'NO' WHERE drId = ?",
                driverID
        );
    }
    @Override
    public boolean updateAvailableYes(String bId) throws SQLException {
        return SQLUtil.execute("UPDATE driver SET availability = 'YES' WHERE drId IN (SELECT drId FROM booking WHERE bId = ?)",
                bId
        );
    }
    @Override
    public String generateNextId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT drId FROM driver ORDER BY drId DESC LIMIT 1");

        String currentDrId = null;

        if (resultSet.next()) {
            currentDrId = resultSet.getString(1);
            return splitDrId(currentDrId);
        }
        return splitDrId(null);
    }

    private String splitDrId(String currentDrId) {
        if (currentDrId != null) {
            String[] split = currentDrId.split("D");
            int id = Integer.parseInt(split[1]);
            id++;

            if(id==10){
                return "D0" + id;
            }else if(id == 100){
                return "D" + id;
            }
            return "D00" + id;
        }
        return "D001";
    }
    @Override
    public List<DriverInTimeDto> gerDrInTime(String date) throws SQLException {
        List<DriverInTimeDto> dtoList = new ArrayList<>();

        ResultSet resultSet = SQLUtil.execute("SELECT\n"+
                "   d.name,\n"+
                "   l.time\n"+
                "FROM\n"+
                "   user u\n"+
                "       join\n"+
                "   login l on u.userName = l.userName\n"+
                "       join\n"+
                "   driver d on u.userName = d.userName\n"+
                "where l.date = ?",
                date
        );

        while (resultSet.next()){
            String dr_name = resultSet.getString(1);
            String in_time = resultSet.getString(2);

            var dto = new DriverInTimeDto(dr_name, in_time);
            dtoList.add(dto);
        }
        return dtoList;
    }
    @Override
    public List<DriverDto> getAllDrivers(String search) throws SQLException {
        List<DriverDto> driverList = new ArrayList<>();

        try {
            ResultSet resultSet = SQLUtil.execute("SELECT * FROM driver WHERE drId OR name LIKE ?",
                    search
            );

            while (resultSet.next()) {
                DriverDto driver = new DriverDto(
                        resultSet.getString("drId"),
                        resultSet.getString("name"),
                        resultSet.getString("address"),
                        resultSet.getString("email"),
                        resultSet.getString("contact"),
                        resultSet.getString("licenseNo"),
                        resultSet.getString("userName"),
                        resultSet.getString("availability")

                );

                driverList.add(driver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return driverList;
    }
}
