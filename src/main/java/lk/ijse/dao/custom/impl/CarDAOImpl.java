package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.CarDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.CarDto;
import lk.ijse.dto.CarOutDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarDAOImpl implements CarDAO{

    @Override
    public boolean save(CarDto dto) throws SQLException {
        return SQLUtil.execute("INSERT INTO car VALUES(?, ?, ?, ?, ?, ?, ?)",
                dto.getCarNo(),
                dto.getBrand(),
                dto.getAvailability(),
                dto.getCurrentMileage(),
                dto.getKmOneDay(),
                dto.getPriceOneDay(),
                dto.getPriceExtraKm()
        );
    }

    @Override
    public List<CarDto> getAll() throws SQLException {
        List<CarDto> dtoList = new ArrayList<>();

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM car");

        while (resultSet.next()){
            String car_number = resultSet.getString(1);
            String car_brand = resultSet.getString(2);
            String car_availability = resultSet.getString(3);
            double car_mileage = resultSet.getDouble(4);
            double km_day = resultSet.getDouble(5);
            double price_day = resultSet.getDouble(6);
            double price_extra = resultSet.getDouble(7);

            var dto = new CarDto(car_number, car_brand, car_availability, car_mileage, km_day, price_day, price_extra);
            dtoList.add(dto);
        }
        return dtoList;
    }
    @Override
    public boolean update(CarDto dto) throws SQLException {
        return SQLUtil.execute("UPDATE car SET brand = ?, availability = ?, currentMileage = ?, kmOneDay = ?, priceOneDay = ?, priceExtraKm = ? WHERE carNo = ?",
                dto.getBrand(),
                dto.getAvailability(),
                dto.getCurrentMileage(),
                dto.getKmOneDay(),
                dto.getPriceOneDay(),
                dto.getPriceExtraKm(),
                dto.getCarNo()
        );
    }
    @Override
    public CarDto search(String carNo) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM car WHERE carNo = ?",
                carNo
        );

        CarDto dto = null;

        if(resultSet.next()) {
            String car_number = resultSet.getString(1);
            String car_brand = resultSet.getString(2);
            String car_availability = resultSet.getString(3);
            double car_milage = resultSet.getDouble(4);
            double km_day = resultSet.getDouble(5);
            double price_day = resultSet.getDouble(6);
            double price_extra = resultSet.getDouble(7);

            dto = new CarDto(car_number, car_brand, car_availability, car_milage, km_day, price_day, price_extra);
        }

        return dto;
    }
    @Override
    public boolean delete(String carNo) throws SQLException {
        return SQLUtil.execute("DELETE FROM car WHERE carNo = ?",
                carNo
        );
    }
    @Override
    public boolean updateAvailable(String carID) throws SQLException {
        return SQLUtil.execute("UPDATE car SET availability = 'NO' WHERE carNo = ?",
                carID
        );
    }
    @Override
    public boolean updateAvailableYes(String bId) throws SQLException {
        return SQLUtil.execute("UPDATE car SET availability = 'YES' WHERE carNo IN (SELECT carNo FROM booking WHERE bId = ?)",
                bId
        );
    }
    @Override
    public String generateNextId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT carNo FROM car ORDER BY carNo DESC LIMIT 1");

        String currentCarNo = null;

        if (resultSet.next()) {
            currentCarNo = resultSet.getString(1);
            return splitCarNo(currentCarNo);
        }
        return splitCarNo(null);
    }

    private String splitCarNo(String currentCarNo) {
        if (currentCarNo != null) {
            String[] split = currentCarNo.split("CR");
            int id = Integer.parseInt(split[1]);
            id++;

            if(id==10){
                return "CR0" + id;
            }else if(id == 100){
                return "CR" + id;
            }
            return "CR00" + id;
        }
        return "CR001";
    }
    @Override
    public List<CarOutDto> getCarOut() throws SQLException {
        List<CarOutDto> dtoList = new ArrayList<>();

        ResultSet resultSet = SQLUtil.execute("SELECT\n"+
                "   c.carNo,\n"+
                "   c.brand,\n"+
                "   b.pickUpDate\n"+
                "FROM\n"+
                "   car c\n"+
                "       join\n"+
                "   bookingdetail bd on c.carNo = bd.carNo\n"+
                "       join\n"+
                "   booking b on b.bId = bd.bId\n"+
                "where b.status = 'Pending'"
        );

        while (resultSet.next()){
            String car_number = resultSet.getString(1);
            String brand = resultSet.getString(2);
            String pickUp_date = resultSet.getString(3);

            var dto = new CarOutDto(car_number,brand, pickUp_date);
            dtoList.add(dto);
        }
        return dtoList;
    }
}
