package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.*;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.BookDTO;
import lk.ijse.dto.BookingDetailDTO;

import java.sql.Connection;
import java.sql.SQLException;

public class MakeBookingDAOImpl implements MakeBookingDAO {
    BookingDAO bookingDAO = new BookingDAOImpl();
    CarDAO carDAO = new CarDAOImpl();
    DriverDAO driverDAO = new DriverDAOImpl();
    BookingDetailDAO bookingDetailDAO = new BookingDetailDAOImpl();
    @Override
    public boolean makeBooking(BookDTO bookDto) throws SQLException {
        boolean result = false;
        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isBookingSaved = bookingDAO.save(bookDto);
            System.out.println("booking "+ isBookingSaved);
            if (isBookingSaved) {
                for(BookingDetailDTO bookingDetail : bookDto.getBookingList()){
                    boolean isCarUpdated = carDAO.updateAvailable(bookingDetail.getCarNo());
                    boolean isDriverUpdated = driverDAO.updateAvailable(bookingDetail.getDriverId());
                    boolean isBookingDetailSaved = bookingDetailDAO.save(bookingDetail);
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
