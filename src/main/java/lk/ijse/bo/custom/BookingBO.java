package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.*;
import lk.ijse.entity.*;

import java.sql.SQLException;
import java.util.List;

public interface BookingBO extends SuperBO {
    public String generateNextBookingId() throws SQLException;
    public boolean makeBooking(BookDTO dto) throws SQLException;
    public List<CarDto> getAllCars() throws SQLException;
    public CarDto searchCars(String carNo) throws SQLException;
    public boolean updatePendingBooking(PendingDTO dto) throws SQLException;
    public List<PendingDTO> getAllPendings() throws SQLException;
    public boolean deleteBooking(String bId) throws SQLException;
    public List<CompleteDTO> getAllCompletes() throws SQLException;
    public boolean deleteBookingDetail(String bId) throws SQLException;
    public List<DriverDto> getAllDrivers() throws SQLException;
    public DriverDto searchDriver(String id) throws SQLException;
    public List<CustomerDto> getAllCustomers() throws SQLException;
    public CustomerDto searchCustomer(String id) throws SQLException;
}
