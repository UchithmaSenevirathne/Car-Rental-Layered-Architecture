package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.DriverDAO;
import lk.ijse.dto.DriverDto;
import lk.ijse.dto.DriverInTimeDto;
import lk.ijse.entity.Driver;
import lk.ijse.entity.DriverInTime;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DriverDAOImpl implements DriverDAO {
    @Override
    public boolean save(Driver entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO driver VALUES(?, ?, ?, ?, ?, ?, ?, ?)",
                entity.getId(),
                entity.getName(),
                entity.getAddress(),
                entity.getEmail(),
                entity.getContact(),
                entity.getLicenseNo(),
                entity.getUserName(),
                entity.getAvailability()
        );
    }
    @Override
    public List<Driver> getAll() throws SQLException {
        List<Driver> drivers = new ArrayList<>();

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

            var entity = new Driver(dr_id, dr_name, dr_address, dr_email, dr_contact, dr_licenseNo, dr_userName, dr_availability);
            drivers.add(entity);
        }
        return drivers;
    }
    @Override
    public boolean update(Driver entity) throws SQLException {
        return SQLUtil.execute("UPDATE driver SET name = ?, address = ?, email = ?, contact = ?, licenseNo = ?, userName = ?, availability = ? WHERE drId = ?",
                entity.getName(),
                entity.getAddress(),
                entity.getEmail(),
                entity.getContact(),
                entity.getLicenseNo(),
                entity.getUserName(),
                entity.getAddress(),
                entity.getId()
        );
    }
    @Override
    public Driver search(String id) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM driver WHERE drId = ?",
                id
        );

        Driver entity = null;

        if (resultSet.next()) {
            String dr_id = resultSet.getString(1);
            String dr_name = resultSet.getString(2);
            String dr_address = resultSet.getString(3);
            String dr_email = resultSet.getString(4);
            String dr_contact = resultSet.getString(5);
            String dr_licenseNo = resultSet.getString(6);
            String dr_userName = resultSet.getString(7);
            String dr_availability = resultSet.getString(8);

            entity = new Driver(dr_id, dr_name, dr_address, dr_email, dr_contact, dr_licenseNo, dr_userName, dr_availability);
        }
        return entity;
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
    public List<DriverInTime> gerDrInTime(String date) throws SQLException {
        List<DriverInTime> driverInTimes = new ArrayList<>();

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

            var entity = new DriverInTime(dr_name, in_time);
            driverInTimes.add(entity);
        }
        return driverInTimes;
    }
    @Override
    public List<Driver> getAllDrivers(String search) throws SQLException {
        List<Driver> drivers = new ArrayList<>();

        try {
            ResultSet resultSet = SQLUtil.execute("SELECT * FROM driver WHERE drId OR name LIKE ?",
                    search
            );

            while (resultSet.next()) {
                Driver driver = new Driver(
                        resultSet.getString("drId"),
                        resultSet.getString("name"),
                        resultSet.getString("address"),
                        resultSet.getString("email"),
                        resultSet.getString("contact"),
                        resultSet.getString("licenseNo"),
                        resultSet.getString("userName"),
                        resultSet.getString("availability")

                );

                drivers.add(driver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return drivers;
    }
}
