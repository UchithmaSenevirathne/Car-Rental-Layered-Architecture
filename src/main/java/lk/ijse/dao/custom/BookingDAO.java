package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Book;
import lk.ijse.entity.BookingDetail;
import lk.ijse.entity.Complete;
import lk.ijse.entity.Pending;

import java.sql.SQLException;
import java.util.List;

public interface BookingDAO extends CrudDAO<Book>{
    boolean UpdateBooking(BookingDetail entity) throws SQLException;

    List<Pending> getAllPendings() throws SQLException;

    boolean updatePendingBooking(Pending entity) throws SQLException;

    List<Complete> getAllCompletes() throws SQLException;

    int getCountBooking() throws SQLException ;
}
