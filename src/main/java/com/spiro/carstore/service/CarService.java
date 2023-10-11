package com.spiro.carstore.service;

import com.spiro.carstore.entity.CarEntity;
import com.spiro.carstore.web.dto.CarDTO;

import java.util.List;

public interface CarService {

    CarDTO createCar(CarDTO carDTO);

    List<CarDTO> createCars(List<CarDTO> carDTOS);

    CarDTO buyCar(Integer userId,Integer carId);

    List<CarDTO> listAllCars();

    List<CarDTO> listAllCarsSold();

    List<CarEntity> listAllAvailableCars();

    boolean isAdmin(Integer userId);

    boolean isUser(Integer userId);
}
