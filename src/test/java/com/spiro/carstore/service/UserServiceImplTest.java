package com.spiro.carstore.service;

import com.spiro.carstore.data.repository.UserEntityRepository;
import com.spiro.carstore.entity.UserEntity;
import com.spiro.carstore.service.impl.UserServiceImpl;
import com.spiro.carstore.web.dto.UserDTO;
import com.spiro.carstore.web.dto.adapter.UserDTOAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@ActiveProfiles("test")
class UserServiceImplTest {

    @Mock
    private UserDTOAdapter userDTOAdapter;

    @Mock
    private UserEntityRepository userEntityRepository;

    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userDTOAdapter, userEntityRepository);
    }

    @Test
    void testCreateUser() {
        UserDTO inputDTO = new UserDTO(); // Initialize with necessary data
        UserEntity userEntity = new UserEntity(); // Initialize with expected entity data

        when(userDTOAdapter.convertFromDTO(inputDTO)).thenReturn(userEntity);
        when(userEntityRepository.save(userEntity)).thenReturn(userEntity);
        when(userDTOAdapter.convertToDTO(userEntity)).thenReturn(inputDTO);

        UserDTO createdUser = userService.createUser(inputDTO);

        verify(userDTOAdapter).convertFromDTO(inputDTO);
        verify(userEntityRepository).save(userEntity);
        verify(userDTOAdapter).convertToDTO(userEntity);

        assertEquals(inputDTO, createdUser);
    }

    @Test
    void testCreateUsers() {
        UserDTO inputDTO = new UserDTO(); // Initialize with necessary data
        List<UserDTO> inputDTOs = Collections.singletonList(inputDTO); // List of input DTOs
        UserEntity userEntity = new UserEntity(); // Initialize with expected entity data

        when(userDTOAdapter.convertFromDTO(inputDTO)).thenReturn(userEntity);
        when(userEntityRepository.save(userEntity)).thenReturn(userEntity);
        when(userDTOAdapter.convertToDTO(userEntity)).thenReturn(inputDTO);

        List<UserDTO> createdUsers = userService.createUsers(inputDTOs);

        verify(userDTOAdapter).convertFromDTO(inputDTO);
        verify(userEntityRepository, times(inputDTOs.size())).save(userEntity);
        verify(userDTOAdapter, times(inputDTOs.size())).convertToDTO(userEntity);

        assertEquals(inputDTOs, createdUsers);
    }

    @Test
    void testListAllUsers() {
        List<UserEntity> userEntities = Collections.singletonList(new UserEntity()); // Initialize with expected entity data
        List<UserDTO> expectedDTOs = Collections.singletonList(new UserDTO()); // Initialize with expected DTO data

        when(userEntityRepository.findAll()).thenReturn(userEntities);
        when(userDTOAdapter.convertToDTO(any(UserEntity.class))).thenReturn(new UserDTO());

        List<UserDTO> allUsers = userService.listAllUsers();

        verify(userEntityRepository).findAll();
        verify(userDTOAdapter, times(userEntities.size())).convertToDTO(any(UserEntity.class));

        assertEquals(expectedDTOs, allUsers);
    }

    @Test
    void testListAllBuyers() {
        List<UserEntity> userEntities = Collections.singletonList(new UserEntity()); // Initialize with expected entity data
        when(userEntityRepository.findAllBuyers()).thenReturn(Optional.of(userEntities));

        List<UserEntity> allBuyers = userService.listAllBuyers();

        verify(userEntityRepository).findAllBuyers();
        assertEquals(userEntities, allBuyers);
    }
}

