package com.spiro.carstore.entity;

import com.spiro.carstore.entity.ids.PricingCoeffEntityId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@IdClass(PricingCoeffEntityId.class)
@Table(name = "pricing_coefficients")
public class PricingCoeffEntity {

    @Id
    private String brand;
    @Id
    private String category;
    @Column(name = "mfg_date_coefficient", nullable = false)
    private Double mfgDateCoefficient;
    @Column(name = "category_coefficient", nullable = false)
    private Double categoryCoefficient;
    @Column(name = "base_price", nullable = false)
    private Double basePrice;

}

