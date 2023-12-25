package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.CarDAO;
import lk.ijse.dto.CarDto;
import lk.ijse.dto.CarOutDto;
import lk.ijse.entity.Car;
import lk.ijse.entity.CarOut;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarDAOImpl implements CarDAO{

    @Override
    public boolean save(Car entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO car VALUES(?, ?, ?, ?, ?, ?, ?)",
                entity.getCarNo(),
                entity.getBrand(),
                entity.getAvailability(),
                entity.getCurrentMileage(),
                entity.getKmOneDay(),
                entity.getPriceOneDay(),
                entity.getPriceExtraKm()
        );
    }

    @Override
    public List<Car> getAll() throws SQLException {
        List<Car> cars = new ArrayList<>();

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM car");

        while (resultSet.next()){
            String car_number = resultSet.getString(1);
            String car_brand = resultSet.getString(2);
            String car_availability = resultSet.getString(3);
            double car_mileage = resultSet.getDouble(4);
            double km_day = resultSet.getDouble(5);
            double price_day = resultSet.getDouble(6);
            double price_extra = resultSet.getDouble(7);

            var entity = new Car(car_number, car_brand, car_availability, car_mileage, km_day, price_day, price_extra);
            cars.add(entity);
        }
        return cars;
    }
    @Override
    public boolean update(Car entity) throws SQLException {
        return SQLUtil.execute("UPDATE car SET brand = ?, availability = ?, currentMileage = ?, kmOneDay = ?, priceOneDay = ?, priceExtraKm = ? WHERE carNo = ?",
                entity.getBrand(),
                entity.getAvailability(),
                entity.getCurrentMileage(),
                entity.getKmOneDay(),
                entity.getPriceOneDay(),
                entity.getPriceExtraKm(),
                entity.getCarNo()
        );
    }
    @Override
    public Car search(String carNo) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM car WHERE carNo = ?",
                carNo
        );

        Car entity = null;

        if(resultSet.next()) {
            String car_number = resultSet.getString(1);
            String car_brand = resultSet.getString(2);
            String car_availability = resultSet.getString(3);
            double car_milage = resultSet.getDouble(4);
            double km_day = resultSet.getDouble(5);
            double price_day = resultSet.getDouble(6);
            double price_extra = resultSet.getDouble(7);

            entity = new Car(car_number, car_brand, car_availability, car_milage, km_day, price_day, price_extra);
        }

        return entity;
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
    public List<CarOut> getCarOut() throws SQLException {
        List<CarOut> carOuts = new ArrayList<>();

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

            var entity = new CarOut(car_number,brand, pickUp_date);
            carOuts.add(entity);
        }
        return carOuts;
    }
}
