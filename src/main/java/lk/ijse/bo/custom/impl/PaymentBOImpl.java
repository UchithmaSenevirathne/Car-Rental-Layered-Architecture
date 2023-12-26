package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.PaymentBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.*;
import lk.ijse.dto.*;
import lk.ijse.entity.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentBOImpl implements PaymentBO {
    CarDAO carDAO = (CarDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CAR);
    DriverDAO driverDAO = (DriverDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.DRIVER);
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    BookingDetailDAO bookingDetailDAO = (BookingDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BOOKING_DETAIL);
    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PAYMENT);
    OneCarPayDAO oneCarPayDAO = (OneCarPayDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ONE_CAR_PAYMENT);

    @Override
    public CarDto searchCar(String carNo) throws SQLException {
        Car car = carDAO.search(carNo);
        CarDto carDto = new CarDto(car.getCarNo(),
                car.getBrand(),
                car.getAvailability(),
                car.getCurrentMileage(),
                car.getKmOneDay(),
                car.getPriceOneDay(),
                car.getPriceExtraKm()
        );
        return carDto;
    }

    @Override
    public DriverDto searchDriver(String id) throws SQLException {
        Driver driver = driverDAO.search(id);
        DriverDto driverDto = new DriverDto(driver.getId(),
                driver.getName(),
                driver.getAddress(),
                driver.getEmail(),
                driver.getContact(),
                driver.getLicenseNo(),
                driver.getUserName(),
                driver.getAvailability()
        );
        return driverDto;
    }

    @Override
    public CustomerDto searchCustomer(String id) throws SQLException {
        Customer customer = customerDAO.search(id);
        CustomerDto customerDto = new CustomerDto(customer.getId(),
                customer.getName(),
                customer.getAddress(),
                customer.getEmail(),
                customer.getContact()
        );
        return customerDto;
    }

    @Override
    public List<BookingDetailDTO> getAllBookings() throws SQLException {
        List<BookingDetail> bookingDetails = bookingDetailDAO.getAll();
        List<BookingDetailDTO> dtoList = new ArrayList<>();

        for (BookingDetail bookingDetail : bookingDetails){
            dtoList.add(new BookingDetailDTO(bookingDetail.getBId(),
                    bookingDetail.getCarNo(),
                    bookingDetail.getDriverId())
            );
        }
        return dtoList;
    }

    @Override
    public boolean savePayment(String bId, double totalPayment, String pickUpDate, BookingDetailDTO bookingDetailDTO) throws SQLException {
        BookingDetail bookingDetail = new BookingDetail(bookingDetailDTO.getBId(),
                bookingDetailDTO.getCarNo(),
                bookingDetailDTO.getDriverId()
        );
        return paymentDAO.savePayment(bId,totalPayment,pickUpDate,bookingDetail);
    }

    @Override
    public List<PaymentDetailDTO> searchPaymentDetail(String bId) throws SQLException {
        List<PaymentDetail> paymentDetails = paymentDAO.searchPaymentDetail(bId);
        List<PaymentDetailDTO> dtoList = new ArrayList<>();

        for (PaymentDetail paymentDetail : paymentDetails){
            dtoList.add(new PaymentDetailDTO(paymentDetail.getBId(),
                    paymentDetail.getPickUpDate(),
                    paymentDetail.getDays(),
                    paymentDetail.getStatus(),
                    paymentDetail.getPayment(),
                    paymentDetail.getCusId(),
                    paymentDetail.getCarNo(),
                    paymentDetail.getDrId())
            );
        }
        return dtoList;
    }

    @Override
    public boolean saveOneCarPay(OneCarPayDTO dto) throws SQLException {
        return oneCarPayDAO.save(new OneCarPay(dto.getBId(),
                dto.getCarNo(),
                dto.getExtraKm(),
                dto.getDriverCost(),
                dto.getSubTotal())
        );
    }
}
