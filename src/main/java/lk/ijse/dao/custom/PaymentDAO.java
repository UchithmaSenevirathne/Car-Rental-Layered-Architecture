package lk.ijse.dao.custom;

import lk.ijse.dto.BookingDetailDTO;
import lk.ijse.dto.PaymentDetailDTO;
import lk.ijse.entity.BookingDetail;
import lk.ijse.entity.PaymentDetail;

import java.sql.SQLException;
import java.util.List;

public interface PaymentDAO {
    List<PaymentDetail> searchPaymentDetail(String bId) throws SQLException;

    boolean savePayment(String bId, double totalPayment, String pickUpDate, BookingDetail bookingDetail) throws SQLException;

}
