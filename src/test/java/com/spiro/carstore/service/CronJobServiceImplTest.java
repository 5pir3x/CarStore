package com.spiro.carstore.service;

import com.spiro.carstore.entity.CarEntity;
import com.spiro.carstore.entity.UserEntity;

import com.spiro.carstore.service.impl.CronJobServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@ActiveProfiles("test")
class CronJobServiceImplTest {

    @Mock
    private CarService carService;

    @Mock
    private UserService userService;

    @InjectMocks
    private CronJobServiceImpl cronJobService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuyAllAvailableCars() {
        // Mock data
        List<UserEntity> buyers = new ArrayList<>();
        UserEntity buyer = new UserEntity();
        buyer.setId(1);
        buyer.setBudget(1000.0);
        buyers.add(buyer);

        List<CarEntity> carsAvailable = new ArrayList<>();
        CarEntity car = new CarEntity();
        car.setId(1);
        car.setPrice(500.0);
        carsAvailable.add(car);

        // Mock dependencies
        when(userService.listAllBuyers()).thenReturn(buyers);
        when(carService.listAllAvailableCars()).thenReturn(carsAvailable);

        // Call the method
        cronJobService.buyAllAvailableCars();

        // Verify that buyCar and budget update methods were called
        verify(carService, times(1)).buyCar(1, 1);
        assertEquals(500.0, buyer.getBudget()); // Check if the budget was updated
    }
}
