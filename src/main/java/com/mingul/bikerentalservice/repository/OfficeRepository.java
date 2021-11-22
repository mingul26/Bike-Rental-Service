package com.mingul.bikerentalservice.repository;

import com.mingul.bikerentalservice.model.Customer;
import com.mingul.bikerentalservice.model.EmailId;
import com.mingul.bikerentalservice.model.Office;
import org.springframework.data.repository.CrudRepository;

public interface OfficeRepository extends CrudRepository<Office, Integer> {

    Iterable<Office> findOfficeByEmailBookContainingAndPassword(EmailId email, String password);
}
