package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.BookingBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.*;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.*;
import lk.ijse.entity.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingBOImpl implements BookingBO {

    BookingDAO bookingDAO = (BookingDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BOOKING);
    CarDAO carDAO = (CarDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CAR);
    DriverDAO driverDAO = (DriverDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.DRIVER);
    BookingDetailDAO bookingDetailDAO = (BookingDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BOOKING_DETAIL);
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public String generateNextBookingId() throws SQLException {
        return bookingDAO.generateNextId();
    }

    @Override
    public boolean makeBooking(BookDTO dto) throws SQLException {
        boolean result = false;
        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            List<BookingDetail> bookingDetailList = new ArrayList<>();
            for (BookingDetailDTO bookingDetailDTO : dto.getBookingList()){
                bookingDetailList.add(new BookingDetail(
                        bookingDetailDTO.getBId(),
                        bookingDetailDTO.getCarNo(),
                        bookingDetailDTO.getDriverId()
                ));
            }
            boolean isBookingSaved = bookingDAO.save(new Book(dto.getBId(),dto.getPickUpDate(),dto.getDays(),dto.getStatus(),dto.getPayment(),dto.getCusId(),bookingDetailList));
            System.out.println("booking "+ isBookingSaved);
            if (isBookingSaved) {
                for(BookingDetail bookingDetail : bookingDetailList){
                    boolean isCarUpdated = carDAO.updateAvailable(bookingDetail.getCarNo());
                    boolean isDriverUpdated = driverDAO.updateAvailable(bookingDetail.getDriverId());
                    boolean isBookingDetailSaved = bookingDetailDAO.save(bookingDetail);
                    if (!isCarUpdated || !isDriverUpdated || !isBookingDetailSaved) {
                        connection.rollback();
                    }
                }
                connection.commit();
                result = true;
            }

        } catch (SQLException e) {
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
        return result;
    }

    @Override
    public List<CarDto> getAllCars() throws SQLException {
        List<Car> cars = carDAO.getAll();
        List<CarDto> carDtos = new ArrayList<>();

        for(Car car : cars){
            carDtos.add(new CarDto(car.getCarNo(),car.getBrand(),car.getAvailability(),car.getCurrentMileage(),car.getKmOneDay(),car.getPriceOneDay(),car.getPriceExtraKm()));
        }
        return carDtos;
    }

    @Override
    public CarDto searchCars(String carNo) throws SQLException {
        Car car = carDAO.search(carNo);
        return new CarDto(car.getCarNo(),car.getBrand(),car.getAvailability(),car.getCurrentMileage(),car.getKmOneDay(),car.getPriceOneDay(),car.getPriceExtraKm());
    }

    @Override
    public boolean updatePendingBooking(PendingDTO dto) throws SQLException {
        return bookingDAO.updatePendingBooking(new Pending(dto.getBId(),dto.getCusId(),dto.getDrId(),dto.getCarNo(),dto.getPickUpDate(),dto.getDays(),dto.getPayment()));
    }

    @Override
    public List<PendingDTO> getAllPendings() throws SQLException {
        List<Pending> pendings = bookingDAO.getAllPendings();
        List<PendingDTO> dtoList = new ArrayList<>();

        for (Pending pending : pendings){
            dtoList.add(new PendingDTO(pending.getBId(), pending.getDrId(), pending.getCarNo(), pending.getCusId(), pending.getPickUpDate(), pending.getDays(), pending.getPayment()));
        }
        return dtoList;
    }

    @Override
    public boolean deleteBooking(String bId) throws SQLException {
        return bookingDAO.delete(bId);
    }

    @Override
    public List<CompleteDTO> getAllCompletes() throws SQLException {
        List<Complete> completes = bookingDAO.getAllCompletes();
        List<CompleteDTO> dtoList = new ArrayList<>();

        for (Complete complete : completes){
            dtoList.add(new CompleteDTO(complete.getBId(), complete.getCusId(), complete.getPickUpDate(), complete.getDays(), complete.getTotalPayment()));
        }
        return dtoList;
    }

    @Override
    public boolean deleteBookingDetail(String bId) throws SQLException {
        return bookingDetailDAO.delete(bId);
    }

    @Override
    public List<DriverDto> getAllDrivers() throws SQLException {
        List<Driver> drivers = driverDAO.getAll();
        List<DriverDto> dtoList = new ArrayList<>();

        for (Driver driver : drivers){
            dtoList.add(new DriverDto(driver.getId(),driver.getName(),driver.getAddress(),driver.getEmail(),driver.getContact(),driver.getLicenseNo(),driver.getUserName(),driver.getAvailability()));
        }
        return dtoList;
    }

    @Override
    public DriverDto searchDriver(String id) throws SQLException {
        Driver driver = driverDAO.search(id);
        return new DriverDto(driver.getId(),driver.getName(),driver.getAddress(),driver.getEmail(),driver.getContact(),driver.getLicenseNo(),driver.getUserName(),driver.getAvailability());
    }

    @Override
    public List<CustomerDto> getAllCustomers() throws SQLException {
       List<Customer> customers = customerDAO.getAll();
       List<CustomerDto> dtoList = new ArrayList<>();

       for (Customer customer : customers){
           dtoList.add(new CustomerDto(customer.getId(), customer.getName(), customer.getAddress(), customer.getEmail(), customer.getContact()));
       }
       return dtoList;
    }

    @Override
    public CustomerDto searchCustomer(String id) throws SQLException {
        Customer customer = customerDAO.search(id);
        return new CustomerDto(customer.getId(), customer.getName(), customer.getAddress(), customer.getEmail(), customer.getContact());
    }
}
