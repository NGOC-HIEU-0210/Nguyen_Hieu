package com.hieu.repository;

import com.hieu.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface MealRepository extends JpaRepository<Meal, Long>, JpaSpecificationExecutor<Meal> {

    Optional<Meal> findByCode(String code);
    boolean existsByCodeAndAirlineId (String code, Long airlineID);
//    boolean existsByAirlineIdAndCodeAndIdNot(Long airlineId, String code);

    List<Meal> findByAirlineId(Long airlineId);

}
