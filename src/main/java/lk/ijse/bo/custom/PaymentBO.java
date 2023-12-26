package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.*;
import java.sql.SQLException;
import java.util.List;

public interface PaymentBO extends SuperBO {
    CarDto searchCar(String carNo) throws SQLException;
    DriverDto searchDriver(String id) throws SQLException;
    CustomerDto searchCustomer(String id) throws SQLException;
    List<BookingDetailDTO> getAllBookings() throws SQLException;
    boolean savePayment(String bId, double totalPayment, String pickUpDate, BookingDetailDTO bookingDetailDTO) throws SQLException;
    List<PaymentDetailDTO> searchPaymentDetail(String bId) throws SQLException;
    boolean saveOneCarPay(OneCarPayDTO dto) throws SQLException;
}
