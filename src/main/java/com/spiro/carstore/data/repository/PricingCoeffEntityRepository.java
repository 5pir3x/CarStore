package com.spiro.carstore.data.repository;

import com.spiro.carstore.entity.PricingCoeffEntity;
import com.spiro.carstore.entity.ids.PricingCoeffEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PricingCoeffEntityRepository extends JpaRepository<PricingCoeffEntity, PricingCoeffEntityId> {

}
