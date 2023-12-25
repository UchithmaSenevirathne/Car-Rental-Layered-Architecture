package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.CarBO;
import lk.ijse.dao.custom.CarDAO;
import lk.ijse.dao.custom.impl.CarDAOImpl;
import lk.ijse.dto.CarDto;
import lk.ijse.dto.CarOutDto;
import lk.ijse.entity.Car;
import lk.ijse.entity.CarOut;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarBOImpl implements CarBO {
    CarDAO carDAO = new CarDAOImpl();

    @Override
    public boolean updateCar(CarDto dto) throws SQLException {
        return carDAO.update(new Car(dto.getCarNo(), dto.getBrand(), dto.getAvailability(), dto.getCurrentMileage(),dto.getKmOneDay(),dto.getPriceOneDay(),dto.getPriceExtraKm()));
    }

    @Override
    public boolean saveCar(CarDto dto) throws SQLException {
        return carDAO.save(new Car(dto.getCarNo(), dto.getBrand(), dto.getAvailability(), dto.getCurrentMileage(),dto.getKmOneDay(),dto.getPriceOneDay(),dto.getPriceExtraKm()));
    }

    @Override
    public List<CarDto> getAllCars() throws SQLException {
        List<Car> cars = carDAO.getAll();
        List<CarDto> carDtos = new ArrayList<>();

        for (Car car : cars){
            carDtos.add(new CarDto(car.getCarNo(), car.getBrand(), car.getAvailability(), car.getCurrentMileage(),car.getKmOneDay(),car.getPriceOneDay(),car.getPriceExtraKm()));
        }
        return carDtos;
    }

    @Override
    public boolean deleteCar(String carNo) throws SQLException {
        return carDAO.delete(carNo);
    }

    @Override
    public String generateNextId() throws SQLException {
        return carDAO.generateNextId();
    }

    @Override
    public List<CarOutDto> getCarOut() throws SQLException {
        List<CarOut> carOuts = carDAO.getCarOut();
        List<CarOutDto> dtoList = new ArrayList<>();

        for (CarOut car : carOuts){
            dtoList.add(new CarOutDto(car.getCarNo(), car.getBrand(), car.getPickUpDate()));
        }
        return dtoList;
    }
}
