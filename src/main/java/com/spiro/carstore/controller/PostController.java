package com.spiro.carstore.controller;

import com.spiro.carstore.service.CarService;
import com.spiro.carstore.service.UserService;
import com.spiro.carstore.web.dto.CarDTO;
import com.spiro.carstore.web.dto.UserDTO;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;

@Controller
public class PostController {
    private final CarService carService;
    private final UserService userService;

    public PostController(CarService carService, UserService userService) {
        this.carService = carService;
        this.userService = userService;
    }

    @QueryMapping
    List<CarDTO> listCars() {
        return carService.listAllCars();
    }

    @QueryMapping
    List<UserDTO> listUsers() {
        return userService.listAllUsers();
    }

    @QueryMapping
    List<CarDTO> listAllCarsSold() {
        return carService.listAllCarsSold();
    }

    @MutationMapping
    CarDTO createCar(@Argument(name = "userId") Integer userId,
                     @Argument(name = "name") String name,
                     @Argument(name = "category") String category,
                     @Argument(name = "brand") String brand,
                     @Argument(name = "description") String description,
                     @Argument(name = "mfcDate") String mfcDate) {
        CarDTO carDTO = new CarDTO();
        carDTO.setUserId(userId);
        carDTO.setName(name);
        carDTO.setCategory(category);
        carDTO.setBrand(brand);
        carDTO.setDescription(description);
        carDTO.setMfcDate(LocalDate.parse(mfcDate));
        return carService.createCar(carDTO);
    }

    @MutationMapping
    UserDTO createUser(@Argument(name = "firstName") String firstName,
                     @Argument(name = "lastName") String lastName,
                     @Argument(name = "email") String email,
                     @Argument(name = "budget") Double budget,
                     @Argument(name = "userType") String userType) {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setEmail(email);
        userDTO.setBudget(budget);
        userDTO.setUserType(userType);
        return userService.createUser(userDTO);
    }

    @MutationMapping
    CarDTO buyCar(@Argument(name = "userId") Integer userId,
                     @Argument(name = "carId") Integer carId) {
        return carService.buyCar(userId,carId);
    }

}
