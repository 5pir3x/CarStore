package com.spiro.carstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "cars")
public class CarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserEntity owner;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "description")
    private String description;

    @Column(name = "mfc_date")
    @Temporal(TemporalType.DATE)
    private LocalDate mfcDate;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "sold_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime soldDate;
}
