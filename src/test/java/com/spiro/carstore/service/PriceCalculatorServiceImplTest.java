package com.spiro.carstore.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.spiro.carstore.data.repository.PricingCoeffEntityRepository;
import com.spiro.carstore.entity.PricingCoeffEntity;
import com.spiro.carstore.entity.ids.PricingCoeffEntityId;
import com.spiro.carstore.service.impl.PriceCalculatorServiceImpl;
import com.spiro.carstore.web.dto.CarDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;
@ActiveProfiles("test")
public class PriceCalculatorServiceImplTest {

    @Mock
    private PricingCoeffEntityRepository pricingCoeffEntityRepository;

    private PriceCalculatorServiceImpl priceCalculatorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        priceCalculatorService = new PriceCalculatorServiceImpl(pricingCoeffEntityRepository);
    }

    @Test
    public void testCalculatePrice() {
        // Arrange
        CarDTO carDTO = new CarDTO();
        carDTO.setBrand("Toyota");
        carDTO.setCategory("SUV");
        carDTO.setMfcDate(LocalDate.of(2020, 1, 1));

        PricingCoeffEntityId pricingId = new PricingCoeffEntityId();
        pricingId.setBrand("TOYOTA");
        pricingId.setCategory("SUV");

        PricingCoeffEntity pricingEntity = new PricingCoeffEntity();

        pricingEntity.setMfgDateCoefficient(1.5);
        pricingEntity.setCategoryCoefficient(2.0);
        pricingEntity.setBasePrice(30000.0);

        when(pricingCoeffEntityRepository.findById(pricingId)).thenReturn(Optional.of(pricingEntity));

        // Act
        Double calculatedPrice = priceCalculatorService.calculatePrice(carDTO);

        // Assert
        assertEquals(27300, calculatedPrice, 0.01);
    }

    @Test
    public void testCalculatePriceWithNewCar() {
        // Arrange
        CarDTO carDTO = new CarDTO();
        carDTO.setBrand("Toyota");
        carDTO.setCategory("SUV");
        carDTO.setMfcDate(LocalDate.of(2023, 1, 1));

        PricingCoeffEntityId pricingId = new PricingCoeffEntityId();
        pricingId.setBrand("TOYOTA");
        pricingId.setCategory("SUV");

        PricingCoeffEntity pricingEntity = new PricingCoeffEntity();

        pricingEntity.setMfgDateCoefficient(1.5);
        pricingEntity.setCategoryCoefficient(2.0);
        pricingEntity.setBasePrice(30000.0);

        when(pricingCoeffEntityRepository.findById(pricingId)).thenReturn(Optional.of(pricingEntity));

        // Act
        Double calculatedPrice = priceCalculatorService.calculatePrice(carDTO);

        // Assert
        assertEquals(30000.0, calculatedPrice, 0.01);
    }

    @Test
    public void testCalculatePriceWithFutureMfcDate() {
        // Arrange
        CarDTO carDTO = new CarDTO();
        carDTO.setBrand("Toyota");
        carDTO.setCategory("SUV");
        carDTO.setMfcDate(LocalDate.now().plusDays(1)); // Future date

        // Act and Assert
        assertThrows(RuntimeException.class, () -> priceCalculatorService.calculatePrice(carDTO));
    }

    @Test
    public void testCalculatePriceWithVeryOldCar() {
        // Arrange
        CarDTO carDTO = new CarDTO();
        carDTO.setBrand("Toyota");
        carDTO.setCategory("SUV");
        carDTO.setMfcDate(LocalDate.of(1950, 1, 1)); // Older car

        PricingCoeffEntityId pricingId = new PricingCoeffEntityId();
        pricingId.setBrand("TOYOTA");
        pricingId.setCategory("SUV");

        PricingCoeffEntity pricingEntity = new PricingCoeffEntity();
//        pricingEntity.setId(pricingId);
        pricingEntity.setMfgDateCoefficient(1.5);
        pricingEntity.setCategoryCoefficient(2.0);
        pricingEntity.setBasePrice(30000.0);

        when(pricingCoeffEntityRepository.findById(pricingId)).thenReturn(Optional.of(pricingEntity));

        // Act
        Double calculatedPrice = priceCalculatorService.calculatePrice(carDTO);

        // Assert
        assertEquals(6000.0, calculatedPrice, 0.01);
    }

    @Test
    public void testCalculatePriceWithLargeCoefficients() {
        // Arrange
        CarDTO carDTO = new CarDTO();
        carDTO.setBrand("Toyota");
        carDTO.setCategory("SUV");
        carDTO.setMfcDate(LocalDate.of(2020, 1, 1));

        PricingCoeffEntityId pricingId = new PricingCoeffEntityId();
        pricingId.setBrand("TOYOTA");
        pricingId.setCategory("SUV");

        PricingCoeffEntity pricingEntity = new PricingCoeffEntity();
//        pricingEntity.setId(pricingId);
        pricingEntity.setMfgDateCoefficient(100.0);
        pricingEntity.setCategoryCoefficient(100.0);
        pricingEntity.setBasePrice(30000.0);

        when(pricingCoeffEntityRepository.findById(pricingId)).thenReturn(Optional.of(pricingEntity));

        // Act
        Double calculatedPrice = priceCalculatorService.calculatePrice(carDTO);

        // Assert
        assertEquals(6000.0, calculatedPrice, 0.01);
    }
}

