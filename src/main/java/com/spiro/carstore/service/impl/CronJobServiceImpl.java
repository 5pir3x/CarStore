package com.spiro.carstore.service.impl;

import com.spiro.carstore.entity.CarEntity;
import com.spiro.carstore.entity.UserEntity;
import com.spiro.carstore.service.CarService;
import com.spiro.carstore.service.CronJobService;
import com.spiro.carstore.service.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CronJobServiceImpl implements CronJobService {

    private final CarService carService;
    private final UserService userService;

    public CronJobServiceImpl(CarService carService, UserService userService) {
        this.carService = carService;
        this.userService = userService;
    }

    @Override
//    @Scheduled(fixedDelay = 5000)
    public void buyAllAvailableCars() {
        System.out.println("Cron job buyAllAvailableCars() starting!!!");
        List<UserEntity> buyers = userService.listAllBuyers();
        List<CarEntity> carsAvailable = carService.listAllAvailableCars();
        for (CarEntity car : carsAvailable) {
            for (UserEntity buyer : buyers) {
                if (buyer.getBudget() >= car.getPrice()) {
                    carService.buyCar(buyer.getId(), car.getId());
                    buyer.setBudget(buyer.getBudget() - car.getPrice());
                    break;
                }
            }
        }
    }
}
