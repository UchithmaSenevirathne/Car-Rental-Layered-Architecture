package lk.ijse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Schedule {
    private String bId;
    private String name;
    private String brand;
    private String address;
    private String contact;
    private String pickUpDate;
    private int days;
}
