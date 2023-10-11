package com.spiro.carstore.service;

import com.spiro.carstore.data.repository.CarEntityRepository;
import com.spiro.carstore.data.repository.UserEntityRepository;
import com.spiro.carstore.entity.CarEntity;
import com.spiro.carstore.entity.UserEntity;
import com.spiro.carstore.entity.UserTypeEntity;
import com.spiro.carstore.service.impl.CarServiceImpl;
import com.spiro.carstore.web.dto.CarDTO;
import com.spiro.carstore.web.dto.adapter.CarDTOAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.boot.test.context.SpringBootTest;


import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class CarServiceImplTest {

    @Mock
    private CarEntityRepository carEntityRepository;

    @Mock
    private UserEntityRepository userEntityRepository;

    @Mock
    private CarDTOAdapter carDTOAdapter;

    @Mock
    private NotificationService notificationService;

    private CarServiceImpl carService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        carService = new CarServiceImpl(carEntityRepository, userEntityRepository, carDTOAdapter, notificationService);

    }

    @Test
    void testCreateCar() {
        CarDTO inputDTO = new CarDTO(); // Initialize with necessary data
        inputDTO.setUserId(1);
        UserEntity userEntity = new UserEntity(); // Initialize with user data
        UserTypeEntity typeEntity = new UserTypeEntity();
        typeEntity.setTypeName("ADMIN");
        typeEntity.setId(1);
        userEntity.setUserTypeEntity(typeEntity);

        CarEntity carEntity = new CarEntity(); // Initialize with expected entity data

        when(userEntityRepository.findById(1)).thenReturn(Optional.of(userEntity));
        when(carDTOAdapter.convertFromDTO(inputDTO)).thenReturn(carEntity);
        when(carEntityRepository.save(carEntity)).thenReturn(carEntity);
        when(carDTOAdapter.convertToDTO(carEntity)).thenReturn(inputDTO);


        CarDTO createdCar = carService.createCar(inputDTO);

        verify(carDTOAdapter).convertFromDTO(inputDTO);
        verify(carEntityRepository).save(carEntity);
        verify(carDTOAdapter).convertToDTO(carEntity);

        assertEquals(inputDTO, createdCar);
    }


    @Test
    void testCreateCarWithoutAdminRights() {
        CarDTO inputDTO = new CarDTO(); // Initialize with necessary data
        inputDTO.setUserId(1);
        UserEntity userEntity = new UserEntity(); // Initialize with user data
        UserTypeEntity typeEntity = new UserTypeEntity();
        typeEntity.setTypeName("USER");
        typeEntity.setId(1);
        userEntity.setUserTypeEntity(typeEntity);
        when(userEntityRepository.findById(1)).thenReturn(Optional.of(userEntity));
        assertThrows(RuntimeException.class, () -> carService.createCar(inputDTO));
    }

    @Test
    void testCreateCars() {
        CarDTO inputDTO = new CarDTO(); // Initialize with necessary data

        inputDTO.setUserId(1);
        inputDTO.setBrand("BMW");
        inputDTO.setDescription("Test desc");
        inputDTO.setMfcDate(LocalDate.now());
        CarEntity carEntity = new CarEntity(); // Initialize with expected entity data
        int userId = 1;
        UserEntity userEntity = new UserEntity(); // Initialize with user data
        UserTypeEntity typeEntity = new UserTypeEntity();
        typeEntity.setTypeName("ADMIN");
        typeEntity.setId(1);
        userEntity.setUserTypeEntity(typeEntity);

        when(userEntityRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(carDTOAdapter.convertFromDTO(inputDTO)).thenReturn(carEntity);
        when(carEntityRepository.save(carEntity)).thenReturn(carEntity);
        when(carDTOAdapter.convertToDTO(carEntity)).thenReturn(inputDTO);


        List<CarDTO> createdCars = carService.createCars(Collections.singletonList(inputDTO));

        verify(carDTOAdapter).convertFromDTO(inputDTO);
        verify(carEntityRepository).save(carEntity);
        verify(carDTOAdapter).convertToDTO(carEntity);

        assertEquals(1, createdCars.size());
        assertEquals(inputDTO, createdCars.get(0));
    }

    @Test
    void testBuyCar() {
        int userId = 1;
        int carId = 2;
        UserEntity userEntity = new UserEntity(); // Initialize with user data
        userEntity.setFirstName("Test");
        userEntity.setLastName("Test Last Name");
        userEntity.setEmail("email@test.com");
        userEntity.setId(1);
        UserTypeEntity typeEntity = new UserTypeEntity();
        typeEntity.setTypeName("USER");
        typeEntity.setId(1);
        userEntity.setUserTypeEntity(typeEntity);
        userEntity.setBudget(2.0);

        CarEntity carEntity = new CarEntity(); // Initialize with car data
        carEntity.setPrice(2.0);
        carEntity.setId(2);
        carEntity.setBrand("BMW");
        carEntity.setDescription("Test desc");
        carEntity.setMfcDate(LocalDate.now());

        when(userEntityRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(carEntityRepository.findAvailableById(carId)).thenReturn(Optional.of(carEntity));
        when(carDTOAdapter.convertToDTO(carEntity)).thenReturn(new CarDTO()); // Adjust for expected DTO
        when(carEntityRepository.save(carEntity)).thenReturn(carEntity);

        CarDTO boughtCar = carService.buyCar(userId, carId);

        verify(userEntityRepository, times(2)).findById(userId);
        verify(carEntityRepository).findAvailableById(carId);
        verify(notificationService).sendMail(anyString(), anyString(), anyString()); // Verify notification

        assertNotNull(boughtCar);
    }

    @Test
    void testBuyCarInsufficientBudget() {
        int userId = 1;
        int carId = 2;
        UserEntity userEntity = new UserEntity(); // Initialize with user data
        CarEntity carEntity = new CarEntity(); // Initialize with car data
        carEntity.setPrice(2.0);

        // Adjust userEntity budget to be lower than car price
        userEntity.setBudget(carEntity.getPrice() - 1);

        when(userEntityRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(carEntityRepository.findAvailableById(carId)).thenReturn(Optional.of(carEntity));

        assertThrows(RuntimeException.class, () -> carService.buyCar(userId, carId));
    }

    @Test
    void testListAllCars() {
        List<CarEntity> carEntities = Arrays.asList(new CarEntity(), new CarEntity()); // Initialize with car data

        when(carEntityRepository.findAll()).thenReturn(carEntities);
        when(carDTOAdapter.convertToDTO(any())).thenReturn(new CarDTO()); // Adjust for expected DTO

        List<CarDTO> allCars = carService.listAllCars();

        verify(carEntityRepository).findAll();
        verify(carDTOAdapter, times(carEntities.size())).convertToDTO(any());

        assertEquals(carEntities.size(), allCars.size());
    }

    @Test
    void testListAllCarsSold() {
        List<CarEntity> carEntities = Arrays.asList(new CarEntity(), new CarEntity()); // Initialize with car data

        when(carEntityRepository.findAllSold()).thenReturn(carEntities);
        when(carDTOAdapter.convertToDTO(any())).thenReturn(new CarDTO()); // Adjust for expected DTO

        List<CarDTO> allCarsSold = carService.listAllCarsSold();

        verify(carEntityRepository).findAllSold();
        verify(carDTOAdapter, times(carEntities.size())).convertToDTO(any());

        assertEquals(carEntities.size(), allCarsSold.size());
    }

    @Test
    void testListAllAvailableCars() {
        List<CarEntity> carEntities = Arrays.asList(new CarEntity(), new CarEntity()); // Initialize with car data

        when(carEntityRepository.findAllAvailable()).thenReturn(Optional.of(carEntities));

        List<CarEntity> allAvailableCars = carService.listAllAvailableCars();

        verify(carEntityRepository).findAllAvailable();

        assertEquals(carEntities.size(), allAvailableCars.size());
    }

    @Test
    void testIsAdmin() {
        int userId = 1;
        UserEntity userEntity = new UserEntity(); // Initialize with user data
        UserTypeEntity typeEntity = new UserTypeEntity();
        typeEntity.setTypeName("ADMIN");
        typeEntity.setId(1);
        userEntity.setUserTypeEntity(typeEntity);


        when(userEntityRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        boolean isUser = carService.isUser(userId);
        boolean isAdmin = carService.isAdmin(userId);

        verify(userEntityRepository, times(2)).findById(userId);

        assertTrue(isAdmin);
        assertFalse(isUser);
    }

    @Test
    void testIsUser() {
        int userId = 1;
        UserEntity userEntity = new UserEntity(); // Initialize with user data
        UserTypeEntity typeEntity = new UserTypeEntity();
        typeEntity.setTypeName("USER");
        typeEntity.setId(2);
        userEntity.setUserTypeEntity(typeEntity);

        when(userEntityRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        boolean isUser = carService.isUser(userId);
        boolean isAdmin = carService.isAdmin(userId);

        verify(userEntityRepository, times(2)).findById(userId);

        assertTrue(isUser);
        assertFalse(isAdmin);
    }
}
