package com.spiro.carstore.service;

import com.spiro.carstore.web.dto.CarDTO;

public interface PriceCalculatorService {
    Double calculatePrice(CarDTO carDTO);
}
