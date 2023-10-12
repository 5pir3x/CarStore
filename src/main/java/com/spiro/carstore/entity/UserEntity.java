package com.spiro.carstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "budget")
    private Double budget;

    @OneToMany(mappedBy = "owner")
    private List<CarEntity> carEntityList = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_type_id")
    private UserTypeEntity userTypeEntity;
}
