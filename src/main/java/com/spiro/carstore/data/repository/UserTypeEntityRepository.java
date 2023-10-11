package com.spiro.carstore.data.repository;

import com.spiro.carstore.entity.UserTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserTypeEntityRepository extends JpaRepository<UserTypeEntity,Integer> {

    @Query(value = """
            SELECT * FROM user_types
            WHERE type_name = :typeName
            """, nativeQuery = true)
    Optional<UserTypeEntity> findEntityByName(String typeName);
}
