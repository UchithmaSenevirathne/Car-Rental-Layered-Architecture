package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.*;
import lk.ijse.dto.*;
import lk.ijse.entity.BookingDetail;
import lk.ijse.entity.PaymentDetail;

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
    public List<PaymentDetail> searchPaymentDetail(String bId) throws SQLException {
        List<PaymentDetail> paymentDetails = new ArrayList<>();

        ResultSet resultSet = SQLUtil.execute("SELECT\n"+
                "   b.bId,\n"+
                "   b.pickUpDate,\n"+
                "   b.days,\n"+
                "   b.status,\n"+
                "   b.payment,\n"+
                "   b.cusId,\n"+
                "   bd.carNo,\n"+
                "   bd.drId\n"+
                "FROM\n"+
                "   booking b\n"+
                "       LEFT JOIN\n"+
                "   bookingdetail bd ON b.bId = bd.bId\n"+
                "WHERE bd.bId = ?",
                bId
        );

        while (resultSet.next()) {
            String b_Id = resultSet.getString(1);
            String date = resultSet.getString(2);
            int rent_days = resultSet.getInt(3);
            String rent_status = resultSet.getString(4);
            double rent_pay = resultSet.getDouble(5);
            String cus_Id = resultSet.getString(6);
            String car_Id = resultSet.getString(7);
            String driverId = resultSet.getString(8);

            var entity = new PaymentDetail(b_Id,date,rent_days,rent_status,rent_pay,cus_Id,car_Id,driverId);
            paymentDetails.add(entity);
        }

        return paymentDetails;
    }
    @Override
    public  boolean savePayment(String bId, double totalPayment, String pickUpDate, BookingDetail bookingDetail) throws SQLException {
        boolean isSaved = SQLUtil.execute("INSERT INTO payment VALUES(?, ?, ?)",
                bId,
                totalPayment,
                pickUpDate
        );

        if(isSaved){
            boolean isCarUpdated = carDAO.updateAvailableYes(bookingDetail.getBId());
            boolean isDriverUpdated = driverDAO.updateAvailableYes(bookingDetail.getBId());
            boolean isBookingDetailUpdate = bookingDetailDAO.update(bookingDetail);
            boolean isBookingUpdated = bookingDAO.UpdateBooking(bookingDetail);

            if(isCarUpdated && isDriverUpdated && isBookingDetailUpdate && isBookingUpdated){
                return true;
            }
        }

        return false;
    }
}
