package lk.ijse.dao.custom;

import lk.ijse.dto.BookDTO;
import lk.ijse.entity.Book;

import java.sql.SQLException;

public interface MakeBookingDAO {
    boolean makeBooking(Book book) throws SQLException;
}
