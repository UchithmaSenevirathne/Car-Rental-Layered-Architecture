package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.BookingDAO;
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

public class BookingDAOImpl implements BookingDAO {
   @Override
    public String generateNextId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT bId FROM booking ORDER BY bId DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentBookingId = null;

        if (resultSet.next()) {
            currentBookingId = resultSet.getString(1);
            return splitBookingId(currentBookingId);
        }
        return splitBookingId(null);
    }

    private static String splitBookingId(String currentBookingId) {
        if (currentBookingId != null) {
            String[] split = currentBookingId.split("B");
            int id = Integer.parseInt(split[1]);
            id++;

            if(id==10){
                return "B0" + id;
            }else if(id == 100){
                return "B" + id;
            }
            return "B00" + id;
        }
        return "B001";
    }


    @Override
    public boolean save(BookDTO bookDTO) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO booking VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, bookDTO.getBId());
        pstm.setString(2, bookDTO.getPickUpDate());
        pstm.setInt(3, bookDTO.getDays());
        pstm.setString(4, bookDTO.getStatus());
        pstm.setDouble(5, bookDTO.getPayment());
        pstm.setString(6, bookDTO.getCusId());

        return pstm.executeUpdate() > 0;
    }

    @Override
    public List<BookDTO> getAll() throws SQLException {
        return null;
    }

    @Override
    public BookDTO search(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean update(BookDTO dto) throws SQLException {
        return false;
    }
    @Override
    public boolean UpdateBooking(BookingDetailDTO bookingDetailDTO) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE booking SET status = 'PAID' WHERE bId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, bookingDetailDTO.getBId());

        return pstm.executeUpdate() > 0;
    }
    @Override
    public List<PendingDTO> getAllPendings() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "select b.bId,b.cusId,b.pickUpDate,b.days,b.payment,bd.drId,bd.carNo from bookingDetail bd left join booking b on b.bId = bd.bId where status = 'Pending'";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<PendingDTO> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            String rent_id = resultSet.getString(1);
            String cus_id = resultSet.getString(2);
            String pickUp_date = resultSet.getString(3);
            int days = resultSet.getInt(4);
            double advance = resultSet.getDouble(5);
            String dr_id = resultSet.getString(6);
            String car_no = resultSet.getString(7);

            var dto = new PendingDTO(rent_id,cus_id,dr_id,car_no,pickUp_date,days,advance);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public boolean delete(String bId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm2 = connection.prepareStatement("DELETE FROM booking WHERE bId = ?");

        pstm2.setString(1, bId);

        return pstm2.executeUpdate() > 0;
    }
    @Override
    public boolean updatePendingBooking(PendingDTO dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm1 = connection.prepareStatement("UPDATE bookingdetail SET carNo = ?, drId = ? WHERE bId = ?");

        pstm1.setString(1, dto.getCarNo());
        pstm1.setString(2, dto.getDrId());
        pstm1.setString(3, dto.getBId());

        if(pstm1.executeUpdate() > 0){
            PreparedStatement pstm2 = connection.prepareStatement("UPDATE booking SET pickUpDate = ?, days = ?, payment = ?, cusId = ? WHERE bId = ?");

            pstm2.setString(1, dto.getPickUpDate());
            pstm2.setInt(2, dto.getDays());
            pstm2.setDouble(3, dto.getPayment());
            pstm2.setString(4, dto.getCusId());
            pstm2.setString(5, dto.getBId());

            return pstm2.executeUpdate() > 0;
        }

        return false;
    }
    @Override
   public List<CompleteDTO> getAllCompletes() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "select b.bId,b.cusId,b.pickUpDate,b.days,p.totalPayment from booking b join payment p on b.bId = p.bId where b.status = 'PAID'";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<CompleteDTO> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            String rent_id = resultSet.getString(1);
            String cus_id = resultSet.getString(2);
            String pickUp_date = resultSet.getString(3);
            int days = resultSet.getInt(4);
            double total = resultSet.getDouble(5);

            var dto = new CompleteDTO(rent_id,cus_id,pickUp_date,days,total);
            dtoList.add(dto);
        }
        return dtoList;
    }
    @Override
    public int getCountBooking() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "select count(bId) from booking";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        int count = 0;

        while (resultSet.next()){
            count = resultSet.getInt(1);
        }
        return count;
    }
}
