package com.spiro.carstore.service.impl;

import com.spiro.carstore.data.repository.PricingCoeffEntityRepository;
import com.spiro.carstore.entity.PricingCoeffEntity;
import com.spiro.carstore.entity.ids.PricingCoeffEntityId;
import com.spiro.carstore.service.PriceCalculatorService;
import com.spiro.carstore.web.dto.CarDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PriceCalculatorServiceImpl implements PriceCalculatorService {

    private final PricingCoeffEntityRepository pricingCoeffEntityRepository;

    public PriceCalculatorServiceImpl(PricingCoeffEntityRepository pricingCoeffEntityRepository) {
        this.pricingCoeffEntityRepository = pricingCoeffEntityRepository;
    }

    @Override
    public Double calculatePrice(CarDTO carDTO) {
        PricingCoeffEntityId prcingId = new PricingCoeffEntityId();
        prcingId.setBrand(carDTO.getBrand().toUpperCase());
        prcingId.setCategory(carDTO.getCategory().toUpperCase());
        PricingCoeffEntity pricingEntity = pricingCoeffEntityRepository.findById(prcingId)
                .orElseThrow(() -> new RuntimeException("No pricing found for brand: " + carDTO.getBrand() + " in category: " + carDTO.getCategory()));
        //Price calculator formula = > (1 - (Years old * mfgDateCoefficient * categoryCoefficient/100)) * base price
        //but no less than 20% of base price
        Integer yearsOld = LocalDate.now().getYear() - carDTO.getMfcDate().getYear();
        if(LocalDate.now().isBefore(carDTO.getMfcDate())) {
            throw new RuntimeException("MfcDate can't be in the future");
        }
        if(yearsOld * pricingEntity.getMfgDateCoefficient() * pricingEntity.getCategoryCoefficient() < 80) {
            return (1 - (yearsOld * pricingEntity.getMfgDateCoefficient() * pricingEntity.getCategoryCoefficient()) / 100) * pricingEntity.getBasePrice();
        } else {
            return pricingEntity.getBasePrice() * 0.20;
        }
    }
}
