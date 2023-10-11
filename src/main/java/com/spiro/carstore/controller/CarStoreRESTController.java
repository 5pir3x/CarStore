package com.spiro.carstore.controller;

import com.spiro.carstore.service.CarService;
import com.spiro.carstore.service.UserService;
import com.spiro.carstore.web.dto.CarDTO;
import com.spiro.carstore.web.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CarStoreRESTController {

    private final CarService carService;
    private final UserService userService;

    @Autowired
    public CarStoreRESTController(CarService carService, UserService userService) {
        this.carService = carService;
        this.userService = userService;
    }

    @RequestMapping(value = "/create-car",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public CarDTO createCar(@RequestBody CarDTO carDTO) {
        return carService.createCar(carDTO);
    }

    @RequestMapping(value = "/create-cars",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CarDTO> createCars(@RequestBody List<CarDTO> carDTOs) {
        return carService.createCars(carDTOs);
    }

    @RequestMapping(value = "/list-cars",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CarDTO> listCars() {
        return carService.listAllCars();
    }

    @RequestMapping(value = "/create-user",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @RequestMapping(value = "/create-users",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDTO> createUsers(@RequestBody List<UserDTO> userDTOs) {
        return userService.createUsers(userDTOs);
    }

    @RequestMapping(value = "/list-users",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDTO> listUsers() {
        return userService.listAllUsers();
    }

    @RequestMapping(value = "/buy",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public CarDTO buyCar(@RequestParam Integer userId, @RequestParam Integer carId) {
        return carService.buyCar(userId,carId);
    }

    @RequestMapping(value = "/history-log",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CarDTO> listCarsHistory() {
        return carService.listAllCarsSold();
    }
}
