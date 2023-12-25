package lk.ijse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OneCarPay {
    private String bId;
    private String carNo;
    private double extraKm;
    private double driverCost;
    private double subTotal;
}
