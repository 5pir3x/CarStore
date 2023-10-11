package com.spiro.carstore.web.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {

    private String firstName;
    private String lastName;
    private String email;
    private Double budget;
    private String userType;
    private List<CarDTO> ownedCars = new ArrayList<>();

}
