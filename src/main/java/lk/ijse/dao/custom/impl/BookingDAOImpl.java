package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.BookingDAO;
import lk.ijse.dto.BookDTO;
import lk.ijse.dto.BookingDetailDTO;
import lk.ijse.dto.CompleteDTO;
import lk.ijse.dto.PendingDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingDAOImpl implements BookingDAO {
   @Override
    public String generateNextId() throws SQLException {
       ResultSet resultSet = SQLUtil.execute("SELECT bId FROM booking ORDER BY bId DESC LIMIT 1");

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
        return SQLUtil.execute( "INSERT INTO booking VALUES(?, ?, ?, ?, ?, ?)",
                bookDTO.getBId(),
                bookDTO.getPickUpDate(),
                bookDTO.getDays(),
                bookDTO.getStatus(),
                bookDTO.getPayment(),
                bookDTO.getCusId()
        );
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
        return SQLUtil.execute("UPDATE booking SET status = 'PAID' WHERE bId = ?",
                bookingDetailDTO.getBId()
        );
    }
    @Override
    public List<PendingDTO> getAllPendings() throws SQLException {
        List<PendingDTO> dtoList = new ArrayList<>();
        ResultSet resultSet = SQLUtil.execute("select\n"+
                        "   b.bId,\n"+
                        "   b.cusId,\n"+
                        "   b.pickUpDate,\n"+
                        "   b.days,\n"+
                        "   b.payment,\n"+
                        "   bd.drId,\n"+
                        "   bd.carNo\n"+
                        "from\n"+
                        "   bookingDetail bd\n"+
                        "       left join\n"+
                        "   booking b on b.bId = bd.bId\n"+
                        "where status = 'Pending'"
                );

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
        return SQLUtil.execute("DELETE FROM booking WHERE bId = ?",
                bId
        );
    }
    @Override
    public boolean updatePendingBooking(PendingDTO dto) throws SQLException {
        boolean isUpdate = SQLUtil.execute("UPDATE bookingdetail SET carNo = ?, drId = ? WHERE bId = ?",
                dto.getCarNo(),
                dto.getDrId(),
                dto.getBId()
        );

        if(isUpdate){
            return SQLUtil.execute("UPDATE booking SET pickUpDate = ?, days = ?, payment = ?, cusId = ? WHERE bId = ?",
                    dto.getPickUpDate(),
                    dto.getDays(),
                    dto.getPayment(),
                    dto.getCusId(),
                    dto.getBId()
            );
        }

        return false;
    }
    @Override
   public List<CompleteDTO> getAllCompletes() throws SQLException {
        List<CompleteDTO> dtoList = new ArrayList<>();

        ResultSet resultSet = SQLUtil.execute("select\n"+
                "   b.bId,\n"+
                "   b.cusId,\n"+
                "   b.pickUpDate,\n"+
                "   b.days,\n"+
                "   p.totalPayment\n"+
                "from\n"+
                "   booking b\n"+
                "       join\n"+
                "   payment p on b.bId = p.bId\n"+
                "where b.status = 'PAID'"
        );

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
        ResultSet resultSet = SQLUtil.execute("select count(bId) from booking");

        int count = 0;

        while (resultSet.next()){
            count = resultSet.getInt(1);
        }
        return count;
    }
}
