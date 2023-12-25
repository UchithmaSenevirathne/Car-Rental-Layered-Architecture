package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.BookingDetailDAO;
import lk.ijse.dto.BookingDetailDTO;
import lk.ijse.entity.BookingDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingDetailDAOImpl implements BookingDetailDAO {
    @Override
    public boolean save(BookingDetail entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO bookingDetail VALUES(?, ?, ?)",
                entity.getBId(),
                entity.getCarNo(),
                entity.getDriverId()
        );
    }
    @Override
    public List<BookingDetail> getAll() throws SQLException {
        List<BookingDetail> bookingDetails = new ArrayList<>();

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM bookingdetail");

        while (resultSet.next()){
            String book_Id = resultSet.getString(1);
            String car_No = resultSet.getString(2);
            String dr_Id = resultSet.getString(3);

            var entity = new BookingDetail(book_Id,car_No,dr_Id);
            bookingDetails.add(entity);
        }
        return bookingDetails;
    }

    @Override
    public BookingDetail search(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean update(BookingDetail entity) throws SQLException {
        return true;
    }

    @Override
    public boolean delete(String bId) throws SQLException {
        return SQLUtil.execute("DELETE FROM bookingdetail WHERE bId = ?",
                bId
        );
    }

    @Override
    public String generateNextId() throws SQLException {
        return null;
    }
}
