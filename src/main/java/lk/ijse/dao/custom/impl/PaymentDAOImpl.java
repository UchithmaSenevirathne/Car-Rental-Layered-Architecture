package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.*;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO{
    CarDAO carDAO = new CarDAOImpl();
    DriverDAO driverDAO = new DriverDAOImpl();
    BookingDAO bookingDAO = new BookingDAOImpl();
    BookingDetailDAO bookingDetailDAO = new BookingDetailDAOImpl();
    @Override
    public List<PaymentDetailDTO> searchPaymentDetail(String bId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT b.bId, b.pickUpDate, b.days, b.status, b.payment, b.cusId, bd.carNo, bd.drId FROM booking b LEFT JOIN bookingdetail bd ON b.bId = bd.bId WHERE bd.bId = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, bId);

        List<PaymentDetailDTO> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String b_Id = resultSet.getString(1);
            String date = resultSet.getString(2);
            int rent_days = resultSet.getInt(3);
            String rent_status = resultSet.getString(4);
            double rent_pay = resultSet.getDouble(5);
            String cus_Id = resultSet.getString(6);
            String car_Id = resultSet.getString(7);
            String driverId = resultSet.getString(8);

            var dto = new PaymentDetailDTO(b_Id,date,rent_days,rent_status,rent_pay,cus_Id,car_Id,driverId);
            dtoList.add(dto);
        }

        return dtoList;
    }
    @Override
    public  boolean savePayment(String bId, double totalPayment, String pickUpDate, BookingDetailDTO bookingDetailDTO) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO payment VALUES(?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, bId);
        pstm.setDouble(2, totalPayment);
        pstm.setString(3, pickUpDate);

        boolean isSaved = pstm.executeUpdate() > 0;

        if(isSaved){
            boolean isCarUpdated = carDAO.updateAvailableYes(bookingDetailDTO.getBId());
            boolean isDriverUpdated = driverDAO.updateAvailableYes(bookingDetailDTO.getBId());
            boolean isBookingDetailUpdate = bookingDetailDAO.update(bookingDetailDTO);
            boolean isBookingUpdated = bookingDAO.UpdateBooking(bookingDetailDTO);

            if(isCarUpdated && isDriverUpdated && isBookingDetailUpdate && isBookingUpdated){
                return true;
            }
        }

        return false;
    }
}
