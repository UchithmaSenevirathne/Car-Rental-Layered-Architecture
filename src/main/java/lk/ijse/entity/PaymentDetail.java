package lk.ijse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentDetail {
    private String bId;
    private String pickUpDate;
    private int days;
    private String status;
    private double payment;
    private String cusId;
    private String carNo;
    private String drId;
}
