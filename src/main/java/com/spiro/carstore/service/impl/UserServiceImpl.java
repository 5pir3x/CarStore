package com.spiro.carstore.service.impl;

import com.spiro.carstore.data.repository.UserEntityRepository;
import com.spiro.carstore.entity.UserEntity;
import com.spiro.carstore.service.UserService;
import com.spiro.carstore.web.dto.UserDTO;
import com.spiro.carstore.web.dto.adapter.UserDTOAdapter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDTOAdapter userDTOAdapter;
    private final UserEntityRepository userEntityRepository;

    @Autowired
    public UserServiceImpl(UserDTOAdapter userDTOAdapter, UserEntityRepository userEntityRepository) {
        this.userDTOAdapter = userDTOAdapter;
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {

        UserEntity userEntity = userDTOAdapter.convertFromDTO(userDTO);
        userEntity = userEntityRepository.save(userEntity);
        return userDTOAdapter.convertToDTO(userEntity);
    }

    @Override
    @Transactional
    public List<UserDTO> createUsers(List<UserDTO> userDTOs) {
        List<UserDTO> createdUsers;
        createdUsers = userDTOs.stream()
                .map(this::createUser).toList();
        return createdUsers;
    }

    @Override
    public List<UserDTO> listAllUsers() {
        List<UserEntity> allUsers = userEntityRepository.findAll();
        return allUsers.stream()
                .map(userDTOAdapter::convertToDTO)
                .toList();
    }

    @Override
    public List<UserEntity> listAllBuyers() {
        return  userEntityRepository.findAllBuyers()
                .orElseThrow(() -> new RuntimeException("No buyers found"))
                .stream().toList();
    }


}
