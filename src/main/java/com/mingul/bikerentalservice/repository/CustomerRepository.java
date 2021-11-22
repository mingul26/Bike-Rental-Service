package com.mingul.bikerentalservice.repository;

import com.mingul.bikerentalservice.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    Iterable<Customer> findCustomerByEmailIdAndPassword(String email, String password);
    Customer findCustomerByEmailId(String emailId);
}
