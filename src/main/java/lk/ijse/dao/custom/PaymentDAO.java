package lk.ijse.dao.custom;

import lk.ijse.dto.BookingDetailDTO;
import lk.ijse.dto.PaymentDetailDTO;

import java.sql.SQLException;
import java.util.List;

public interface PaymentDAO {
    List<PaymentDetailDTO> searchPaymentDetail(String bId) throws SQLException;

    boolean savePayment(String bId, double totalPayment, String pickUpDate, BookingDetailDTO bookingDetailDTO) throws SQLException;

}
