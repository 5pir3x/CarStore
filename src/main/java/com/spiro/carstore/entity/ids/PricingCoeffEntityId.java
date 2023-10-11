package com.spiro.carstore.entity.ids;

import lombok.Data;

import java.io.Serializable;

@Data
public class PricingCoeffEntityId implements Serializable {

    private String brand;
    private String category;
}
