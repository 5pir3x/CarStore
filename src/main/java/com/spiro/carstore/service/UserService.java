package com.spiro.carstore.service;

import com.spiro.carstore.entity.UserEntity;
import com.spiro.carstore.web.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);

    List<UserDTO> createUsers(List<UserDTO> userDTOs);

    List<UserDTO> listAllUsers();

    List<UserEntity> listAllBuyers();
}
