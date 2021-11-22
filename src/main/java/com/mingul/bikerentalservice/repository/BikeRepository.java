package com.mingul.bikerentalservice.repository;

import com.mingul.bikerentalservice.model.Bike;
import com.mingul.bikerentalservice.model.Office;
import org.springframework.data.repository.CrudRepository;

public interface BikeRepository extends CrudRepository<Bike, Integer> {
    Iterable<Bike> findBikesBySourceLocation(Office office);
    Iterable<Bike> findBikesByTargetLocation(Office office);
}
