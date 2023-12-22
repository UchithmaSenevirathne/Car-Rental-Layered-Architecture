package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.BookingDetailDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.BookingDetailDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingDetailDAOImpl implements BookingDetailDAO {
    @Override
    public boolean save(BookingDetailDTO bookingDetail) throws SQLException {
        return SQLUtil.execute("INSERT INTO bookingDetail VALUES(?, ?, ?)",
                bookingDetail.getBId(),
                bookingDetail.getCarNo(),
                bookingDetail.getDriverId()
        );
    }
    @Override
    public List<BookingDetailDTO> getAll() throws SQLException {
        List<BookingDetailDTO> dtoList = new ArrayList<>();

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM bookingdetail");

        while (resultSet.next()){
            String book_Id = resultSet.getString(1);
            String car_No = resultSet.getString(2);
            String dr_Id = resultSet.getString(3);

            var dto = new BookingDetailDTO(book_Id,car_No,dr_Id);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public BookingDetailDTO search(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean update(BookingDetailDTO dto) throws SQLException {
        return false;
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
