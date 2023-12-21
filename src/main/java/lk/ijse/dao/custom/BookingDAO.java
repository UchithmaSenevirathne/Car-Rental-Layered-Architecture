package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.BookDTO;
import lk.ijse.dto.BookingDetailDTO;
import lk.ijse.dto.CompleteDTO;
import lk.ijse.dto.PendingDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface BookingDAO extends CrudDAO<BookDTO>{
    public boolean UpdateBooking(BookingDetailDTO bookingDetailDTO) throws SQLException;
    public List<PendingDTO> getAllPendings() throws SQLException;
    public boolean updatePendingBooking(PendingDTO dto) throws SQLException;

    public List<CompleteDTO> getAllCompletes() throws SQLException;

    public int getCountBooking() throws SQLException ;
}
