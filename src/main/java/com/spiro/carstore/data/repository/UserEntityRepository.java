package com.spiro.carstore.data.repository;


import com.spiro.carstore.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Integer> {

    @Query(value = """
            SELECT * FROM users
            WHERE users.user_type_id = 2
            AND budget > 0.0
            ORDER BY budget DESC
            """, nativeQuery = true)
    Optional<List<UserEntity>> findAllBuyers();
}
