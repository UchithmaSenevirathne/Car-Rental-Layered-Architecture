package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.*;
import java.sql.SQLException;
import java.util.List;

public interface BookingBO extends SuperBO {
    String generateNextBookingId() throws SQLException;
    boolean makeBooking(BookDTO dto) throws SQLException;
    List<CarDto> getAllCars() throws SQLException;
    CarDto searchCars(String carNo) throws SQLException;
    boolean updatePendingBooking(PendingDTO dto) throws SQLException;
    List<PendingDTO> getAllPendings() throws SQLException;
    boolean deleteBooking(String bId) throws SQLException;
    List<CompleteDTO> getAllCompletes() throws SQLException;
    boolean deleteBookingDetail(String bId) throws SQLException;
    List<DriverDto> getAllDrivers() throws SQLException;
    DriverDto searchDriver(String id) throws SQLException;
    List<CustomerDto> getAllCustomers() throws SQLException;
    CustomerDto searchCustomer(String id) throws SQLException;
}
