package lk.ijse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Car {
    private String carNo;
    private String brand;
    private String availability;
    private double currentMileage;
    private double kmOneDay;
    private double priceOneDay;
    private double priceExtraKm;
}
