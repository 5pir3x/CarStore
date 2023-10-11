package com.spiro.carstore.web.dto.adapter;

import com.spiro.carstore.data.repository.UserEntityRepository;
import com.spiro.carstore.entity.CarEntity;
import com.spiro.carstore.entity.UserEntity;
import com.spiro.carstore.service.PriceCalculatorService;
import com.spiro.carstore.web.dto.CarDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CarDTOAdapter {


    private final UserEntityRepository userEntityRepository;
    private final PriceCalculatorService priceCalculatorService;

    @Autowired
    public CarDTOAdapter(UserEntityRepository userEntityRepository, PriceCalculatorService priceCalculatorService) {
        this.userEntityRepository = userEntityRepository;
        this.priceCalculatorService = priceCalculatorService;
    }

    public CarDTO convertToDTO(CarEntity carEntity) {

        CarDTO carDTO = new CarDTO();
        carDTO.setBrand(carEntity.getBrand());
        carDTO.setName(carEntity.getName());
        carDTO.setCategory(carEntity.getCategory());
        if (carEntity.getOwner() != null) {
            carDTO.setOwnerId(carEntity.getOwner().getId());
            carDTO.setSoldDate(carEntity.getSoldDate());
        }
        carDTO.setDescription(carEntity.getDescription());
        carDTO.setMfcDate(carEntity.getMfcDate());
        carDTO.setPrice(String.valueOf(carEntity.getPrice()));
        carDTO.setCarId(carEntity.getId());
        return carDTO;
    }

    public CarEntity convertFromDTO(CarDTO carDTO) {

        CarEntity carEntity = new CarEntity();
        carEntity.setBrand(carDTO.getBrand());
        carEntity.setName(carDTO.getName());
        carEntity.setCategory(carDTO.getCategory());
        if (carDTO.getOwnerId() != null) {
            UserEntity owner = userEntityRepository.findById(carDTO.getOwnerId())
                    .orElseThrow(() -> new RuntimeException("User with ID: " + carDTO.getOwnerId() + " not found"));

            carEntity.setOwner(owner);
            carEntity.setSoldDate(carDTO.getSoldDate());
        }
        carEntity.setDescription(carDTO.getDescription());
        carEntity.setMfcDate(carDTO.getMfcDate());
        Double price = priceCalculatorService.calculatePrice(carDTO);
        carEntity.setPrice(price);
        return carEntity;
    }
}
