package com.spiro.carstore.service.impl;

import com.spiro.carstore.data.repository.CarEntityRepository;
import com.spiro.carstore.data.repository.UserEntityRepository;
import com.spiro.carstore.entity.CarEntity;
import com.spiro.carstore.entity.UserEntity;
import com.spiro.carstore.service.CarService;
import com.spiro.carstore.service.NotificationService;
import com.spiro.carstore.web.dto.CarDTO;
import com.spiro.carstore.web.dto.adapter.CarDTOAdapter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    private final CarEntityRepository carEntityRepository;
    private final UserEntityRepository userEntityRepository;
    private final CarDTOAdapter carDTOAdapter;
    private final NotificationService notificationService;

    @Autowired
    public CarServiceImpl(CarEntityRepository carEntityRepository, UserEntityRepository userEntityRepository, CarDTOAdapter carDTOAdapter, NotificationService notificationService) {
        this.carEntityRepository = carEntityRepository;
        this.userEntityRepository = userEntityRepository;
        this.carDTOAdapter = carDTOAdapter;
        this.notificationService = notificationService;
    }

    @Override
    public CarDTO createCar(CarDTO carDTO) {
        if (isAdmin(carDTO.getUserId())) {
            CarEntity carEntity = carDTOAdapter.convertFromDTO(carDTO);
            carEntity = carEntityRepository.save(carEntity);
            return carDTOAdapter.convertToDTO(carEntity);
        } else {
            throw new RuntimeException("User with userId: " + carDTO.getUserId() + " has no authority to create Cars");
        }
    }

    @Override
    @Transactional
    public List<CarDTO> createCars(List<CarDTO> carDTOs) {
        List<CarDTO> createdCars;
        createdCars = carDTOs.stream()
                .map(this::createCar).toList();
        return createdCars;
    };

    @Override
    @Transactional
    public CarDTO buyCar(Integer userId, Integer carId) {
        if (isUser(userId)) {
            UserEntity userEntity = userEntityRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User with ID: " + userId + " not found"));
            CarEntity carEntity = carEntityRepository.findAvailableById(carId)
                    .orElseThrow(() -> new RuntimeException("Car with ID: " + carId + " not found or not available to be sold"));
            if (userEntity.getBudget() >= carEntity.getPrice()) {
                userEntity.setBudget(userEntity.getBudget() - carEntity.getPrice());
                carEntity.setOwner(userEntity);
                carEntity.setSoldDate(LocalDateTime.now());
                userEntity.getCarEntityList().add(carEntity);
                userEntityRepository.save(userEntity);
                notificationService.sendMail(userEntity.getEmail(),
                        "Car Purchase Confirmation",
                        "Congratulations on your new car purchase");
                return carDTOAdapter.convertToDTO(carEntity);
            } else {
                throw new RuntimeException("User doesn't have enough money");
            }
        }
        throw new RuntimeException("User doesn't have authorization to buy a car");
    }

    @Override
    public List<CarDTO> listAllCars() {
        List<CarEntity> allCars = carEntityRepository.findAll();
        return allCars.stream()
                .map(carDTOAdapter::convertToDTO)
                .toList();
    }

    @Override
    public List<CarDTO> listAllCarsSold() {
        List<CarEntity> allCars = carEntityRepository.findAllSold();
        return allCars.stream()
                .map(carDTOAdapter::convertToDTO)
                .toList();
    }
    @Override
    public List<CarEntity> listAllAvailableCars() {
        return carEntityRepository.findAllAvailable()
                .orElseThrow(() -> new RuntimeException("No available cars to be sold found"))
                .stream().toList();
    }

    @Override
    public boolean isAdmin(Integer userId) {
        String userTypeName = userEntityRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User with ID: " + userId + " not found"))
                .getUserTypeEntity()
                .getTypeName();
        return userTypeName.equalsIgnoreCase("ADMIN");
    }

    @Override
    public boolean isUser(Integer userId) {
        String userTypeName = userEntityRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User with ID: " + userId + " not found"))
                .getUserTypeEntity()
                .getTypeName();
        return userTypeName.equalsIgnoreCase("USER");
    }
}
