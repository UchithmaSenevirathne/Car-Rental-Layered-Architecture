package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.*;
import lk.ijse.entity.*;

import java.sql.SQLException;
import java.util.List;

public interface PaymentBO extends SuperBO {
    public CarDto searchCar(String carNo) throws SQLException;
    public DriverDto searchDriver(String id) throws SQLException;
    public CustomerDto searchCustomer(String id) throws SQLException;
    public List<BookingDetailDTO> getAllBookings() throws SQLException;
    public  boolean savePayment(String bId, double totalPayment, String pickUpDate, BookingDetailDTO bookingDetailDTO) throws SQLException;
    public List<PaymentDetailDTO> searchPaymentDetail(String bId) throws SQLException;
    public boolean saveOneCarPay(OneCarPayDTO dto) throws SQLException;
}
