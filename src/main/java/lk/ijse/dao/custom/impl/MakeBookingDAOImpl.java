package lk.ijse.dao.custom.impl;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.BookDTO;
import lk.ijse.dto.BookingDetailDTO;

import java.sql.Connection;
import java.sql.SQLException;

public class MakeBookingDAOImpl {
    private final BookingDAOImpl bookingModel = new BookingDAOImpl();
    private final CarDAOImpl carModel = new CarDAOImpl();
    private final DriverDAOImpl driverModel = new DriverDAOImpl();
    private final BookingDetailDAOImpl bookingDetailModel = new BookingDetailDAOImpl();

    public boolean makeBooking(BookDTO bookDto) throws SQLException {
        boolean result = false;
        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isBookingSaved = BookingDAOImpl.saveBooking(bookDto.getBId(), bookDto.getPickUpDate(), bookDto.getDays(), bookDto.getStatus(), bookDto.getPayment(), bookDto.getCusId());
            System.out.println("booking "+ isBookingSaved);
            if (isBookingSaved) {
                for(BookingDetailDTO bookingDetail : bookDto.getBookingList()){
                    boolean isCarUpdated = carModel.updateAvailable(bookingDetail.getCarNo());
                    boolean isDriverUpdated = driverModel.updateAvailable(bookingDetail.getDriverId());
                    boolean isBookingDetailSaved = bookingDetailModel.saveBookingDetail(bookingDetail);
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
}
