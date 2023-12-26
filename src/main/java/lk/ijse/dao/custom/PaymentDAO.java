package lk.ijse.dao.custom;

import lk.ijse.dao.SuperDAO;
import lk.ijse.entity.BookingDetail;
import lk.ijse.entity.PaymentDetail;
import java.sql.SQLException;
import java.util.List;

public interface PaymentDAO extends SuperDAO {
    List<PaymentDetail> searchPaymentDetail(String bId) throws SQLException;

    boolean savePayment(String bId, double totalPayment, String pickUpDate, BookingDetail bookingDetail) throws SQLException;

}
