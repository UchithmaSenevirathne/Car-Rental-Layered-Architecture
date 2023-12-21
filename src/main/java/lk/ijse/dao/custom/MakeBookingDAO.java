package lk.ijse.dao.custom;

import lk.ijse.dto.BookDTO;

import java.sql.SQLException;

public interface MakeBookingDAO {
    public boolean makeBooking(BookDTO bookDto) throws SQLException;
}
