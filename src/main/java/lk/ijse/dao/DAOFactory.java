package lk.ijse.dao;

import lk.ijse.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory(){}

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null)? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes{
        BOOKING,BOOKING_DETAIL,CAR,CUSTOMER,DRIVER,MAKE_BOOKING,ONE_CAR_PAYMENT,PAYMENT,SALARY,SCHEDULE,USER
    }

    public SuperDAO getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case BOOKING:
                return new BookingDAOImpl();
            case BOOKING_DETAIL:
                return new BookingDetailDAOImpl();
            case CAR:
                return new CarDAOImpl();
            case CUSTOMER:
                return new CustomerDAOImpl();
            case DRIVER:
                return new DriverDAOImpl();
            case MAKE_BOOKING:
                return new MakeBookingDAOImpl();
            case ONE_CAR_PAYMENT:
                return new OneCarPayDAOImpl();
            case PAYMENT:
                return new PaymentDAOImpl();
            case SALARY:
                return new SalaryDAOImpl();
            case SCHEDULE:
                return new ScheduleDAOImpl();
            case USER:
                return new UserDAOImpl();
            default:
                return null;
        }
    }
}
