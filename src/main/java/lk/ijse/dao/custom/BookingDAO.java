package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dto.BookDTO;
import lk.ijse.dto.BookingDetailDTO;
import lk.ijse.dto.CompleteDTO;
import lk.ijse.dto.PendingDTO;

import java.sql.SQLException;
import java.util.List;

public interface BookingDAO extends CrudDAO<BookDTO>{
    boolean UpdateBooking(BookingDetailDTO bookingDetailDTO) throws SQLException;

    List<PendingDTO> getAllPendings() throws SQLException;

    boolean updatePendingBooking(PendingDTO dto) throws SQLException;

    List<CompleteDTO> getAllCompletes() throws SQLException;

    int getCountBooking() throws SQLException ;
}
