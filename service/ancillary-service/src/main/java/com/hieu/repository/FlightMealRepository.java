package com.hieu.repository;


import com.hieu.model.FlightMeal;
import com.hieu.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface FlightMealRepository extends JpaRepository<FlightMeal, Long>, JpaSpecificationExecutor<FlightMeal> {

    List<FlightMeal> findByFlightId(Long id);

    boolean existsByFlightIdAndMealId(Long id, Long mealId);

    Optional<FlightMeal> findByFlightIdAndMeal(Long flightId, Meal meal);

    void deleteByFlightId(Long flightId);
}
