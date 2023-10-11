package com.spiro.carstore.data.repository;

import com.spiro.carstore.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface CarEntityRepository extends JpaRepository<CarEntity, Integer> {

    @Query(value = """
            SELECT * FROM cars
            WHERE owner_id IS NOT NULL
            ORDER BY sold_date DESC
            """, nativeQuery = true)
    List<CarEntity> findAllSold();
    @Query(value = """
            SELECT * FROM cars
            WHERE id = :carId
            AND owner_id IS NULL
            """, nativeQuery = true)
    Optional<CarEntity> findAvailableById(Integer carId);
    @Query(value = """
            SELECT * FROM cars
            WHERE owner_id IS NULL
            ORDER BY price DESC
            """, nativeQuery = true)
    Optional<List<CarEntity>> findAllAvailable();
}
