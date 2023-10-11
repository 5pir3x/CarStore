package com.spiro.carstore.web.dto.adapter;

import com.spiro.carstore.data.repository.UserEntityRepository;
import com.spiro.carstore.data.repository.UserTypeEntityRepository;
import com.spiro.carstore.entity.UserEntity;
import com.spiro.carstore.entity.UserTypeEntity;
import com.spiro.carstore.web.dto.CarDTO;
import com.spiro.carstore.web.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDTOAdapter {

    private final CarDTOAdapter carDTOAdapter;

    private final UserTypeEntityRepository userTypeEntityRepository;

    @Autowired
    public UserDTOAdapter(CarDTOAdapter carDTOAdapter, UserTypeEntityRepository userTypeEntityRepository) {
        this.carDTOAdapter = carDTOAdapter;
        this.userTypeEntityRepository = userTypeEntityRepository;
    }

    public UserDTO convertToDTO(UserEntity userEntity) {

        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(userEntity.getFirstName());
        userDTO.setLastName(userEntity.getLastName());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setBudget(userEntity.getBudget());
        userDTO.setUserType(userEntity.getUserTypeEntity().getTypeName());

        List<CarDTO> ownedCarsDTO = userEntity.getCarEntityList().stream()
                                        .map(carDTOAdapter::convertToDTO)
                                        .toList();

        userDTO.setOwnedCars(ownedCarsDTO);

        return userDTO;
    }

    public UserEntity convertFromDTO(UserDTO userDTO) {

        UserEntity userEntity = new UserEntity();

        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setLastName(userDTO.getLastName());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setBudget(userDTO.getBudget());

        UserTypeEntity userTypeEntity = userTypeEntityRepository.findEntityByName(userDTO.getUserType().toUpperCase())
                .orElseThrow(() -> new RuntimeException("UserType with type name: " + userDTO.getUserType().toUpperCase() + " not found"));

        userEntity.setUserTypeEntity(userTypeEntity);
        return userEntity;
    }
}
