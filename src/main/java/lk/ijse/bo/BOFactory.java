package lk.ijse.bo;

import lk.ijse.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){}

    public static BOFactory getBoFactory() {
        return (boFactory == null)? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes{
        BOOKING,CAR,CUSTOMER,DRIVER,PAYMENT,SALARY,SCHEDULE,USER
    }

    public SuperBO getBO(BOTypes boTypes){
        switch (boTypes){
            case BOOKING:
                return new BookingBOImpl();
            case CAR:
                return new CarBOImpl();
            case CUSTOMER:
                return new CustomerBOImpl();
            case DRIVER:
                return new DriverBOImpl();
            case PAYMENT:
                return new PaymentBOImpl();
            case SALARY:
                return new SalaryBOImpl();
            case SCHEDULE:
                return new ScheduleBOImpl();
            case USER:
                return new UserBOImpl();
            default:
                return null;
        }
    }
}
