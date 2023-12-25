package lk.ijse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Bill {
    private String bId;
    private String brand;
    private double priceOneDay;
    private int days;
    private double priceExtraKm;
    private double extraKm;
    private double driverCost;
    private double subTotal;
}
