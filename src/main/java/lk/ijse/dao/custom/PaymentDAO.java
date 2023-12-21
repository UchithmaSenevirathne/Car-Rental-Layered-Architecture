package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.BookingDetailDTO;
import lk.ijse.dto.PaymentDetailDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PaymentDAO {
    public List<PaymentDetailDTO> searchPaymentDetail(String bId) throws SQLException;

    public  boolean savePayment(String bId, double totalPayment, String pickUpDate, BookingDetailDTO bookingDetailDTO) throws SQLException;

}
