package lk.ijse.dao.custom;

import lk.ijse.dao.SuperDAO;
import lk.ijse.dto.BookDTO;
import lk.ijse.entity.Book;

import java.sql.SQLException;

public interface MakeBookingDAO extends SuperDAO {
    boolean makeBooking(Book book) throws SQLException;
}
