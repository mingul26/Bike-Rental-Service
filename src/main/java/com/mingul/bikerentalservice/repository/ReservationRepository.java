package com.mingul.bikerentalservice.repository;

import com.mingul.bikerentalservice.model.Bike;
import com.mingul.bikerentalservice.model.Customer;
import com.mingul.bikerentalservice.model.Office;
import com.mingul.bikerentalservice.model.Reservation;
import org.springframework.data.repository.CrudRepository;

public interface ReservationRepository extends CrudRepository<Reservation, Integer> {
    Iterable<Reservation> findReservationByBikeOrderByIdDesc(Bike bike);
    Iterable<Reservation> findReservationByCustomerAndStatusEquals(Customer customer, String status);
    Iterable<Reservation> findReservationByReturnLocationAndStatusEquals(Office office, String status);
    Iterable<Reservation> findReservationByPickupLocationAndStatusEquals(Office office, String status);
}
