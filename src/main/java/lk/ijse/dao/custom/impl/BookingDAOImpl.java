package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.BookingDAO;
import lk.ijse.entity.Book;
import lk.ijse.entity.BookingDetail;
import lk.ijse.entity.Complete;
import lk.ijse.entity.Pending;
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
    public boolean save(Book entity) throws SQLException {
        return SQLUtil.execute( "INSERT INTO booking VALUES(?, ?, ?, ?, ?, ?)",
                entity.getBId(),
                entity.getPickUpDate(),
                entity.getDays(),
                entity.getStatus(),
                entity.getPayment(),
                entity.getCusId()
        );
    }

    @Override
    public List<Book> getAll() throws SQLException {
        return null;
    }

    @Override
    public Book search(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean update(Book entity) throws SQLException {
        return false;
    }
    @Override
    public boolean UpdateBooking(BookingDetail entity) throws SQLException {
        return SQLUtil.execute("UPDATE booking SET status = 'PAID' WHERE bId = ?",
                entity.getBId()
        );
    }
    @Override
    public List<Pending> getAllPendings() throws SQLException {
        List<Pending> pendings = new ArrayList<>();
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

            var entity = new Pending(rent_id,cus_id,dr_id,car_no,pickUp_date,days,advance);
            pendings.add(entity);
        }
        return pendings;
    }

    @Override
    public boolean delete(String bId) throws SQLException {
        return SQLUtil.execute("DELETE FROM booking WHERE bId = ?",
                bId
        );
    }
    @Override
    public boolean updatePendingBooking(Pending entity) throws SQLException {
        boolean isUpdate = SQLUtil.execute("UPDATE bookingdetail SET carNo = ?, drId = ? WHERE bId = ?",
                entity.getCarNo(),
                entity.getDrId(),
                entity.getBId()
        );

        if(isUpdate){
            return SQLUtil.execute("UPDATE booking SET pickUpDate = ?, days = ?, payment = ?, cusId = ? WHERE bId = ?",
                    entity.getPickUpDate(),
                    entity.getDays(),
                    entity.getPayment(),
                    entity.getCusId(),
                    entity.getBId()
            );
        }

        return false;
    }
    @Override
   public List<Complete> getAllCompletes() throws SQLException {
        List<Complete> completes = new ArrayList<>();

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

            var entity = new Complete(rent_id,cus_id,pickUp_date,days,total);
            completes.add(entity);
        }
        return completes;
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
