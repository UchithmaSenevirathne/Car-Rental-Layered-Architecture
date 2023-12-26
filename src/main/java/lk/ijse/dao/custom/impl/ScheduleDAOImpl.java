package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.ScheduleDAO;
import lk.ijse.entity.Schedule;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDAOImpl implements ScheduleDAO {
    @Override
    public List<Schedule> getSchedule(String userName) throws SQLException {
        ResultSet resultSet1 = SQLUtil.execute("SELECT drId FROM driver WHERE userName = ?",
                userName
        );

        String drId = null;

        while (resultSet1.next()) {
            drId = resultSet1.getString(1);
        }
        System.out.println(drId);

        if(!drId.equals(null)) {
            List<Schedule> schedules = new ArrayList<>();

            ResultSet resultSet2 = SQLUtil.execute("select\n"+
                    "   bd.bId,\n"+
                    "   c.name,\n"+
                    "   cr.brand,\n"+
                    "   c.address,\n"+
                    "   c.contact,\n"+
                    "   b.pickUpDate,\n"+
                    "   b.days\n"+
                    "from\n"+
                    "   car cr\n"+
                    "       left join\n"+
                    "   bookingdetail bd on cr.carNo = bd.carNo\n"+
                    "       left join\n"+
                    "   booking b on bd.bId = b.bId\n"+
                    "       left join\n"+
                    "   customer c on b.cusId = c.cusId\n"+
                    "where bd.drId = ?",
                    drId
            );

            while (resultSet2.next()) {
                String b_id = resultSet2.getString(1);
                String cus_name = resultSet2.getString(2);
                String car_brand = resultSet2.getString(3);
                String cus_address = resultSet2.getString(4);
                String cus_contact = resultSet2.getString(5);
                String pickUpDate = resultSet2.getString(6);
                Integer days = resultSet2.getInt(7);

                var entity = new Schedule(b_id,
                        cus_name,
                        car_brand,
                        cus_address,
                        cus_contact,
                        pickUpDate,
                        days);

                schedules.add(entity);
            }
            return schedules;
        }
        return null;
    }
    @Override
    public String getDrName(String userName) throws SQLException {
        ResultSet resultSet1 = SQLUtil.execute("SELECT name FROM driver WHERE userName = ?",
                userName
        );

        String name = null;

        while (resultSet1.next()) {
            name = resultSet1.getString(1);
        }
        return name;
    }
}
