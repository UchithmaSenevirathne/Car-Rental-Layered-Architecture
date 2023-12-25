package lk.ijse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Book {
    private String bId;
    private String pickUpDate;
    private int days;
    private String status;
    private double payment;
    private String cusId;
    private List<BookingDetail> bookingList = new ArrayList<>();

}
