package com.mingul.bikerentalservice.repository;

import com.mingul.bikerentalservice.model.EmailId;
import org.springframework.data.repository.CrudRepository;

public interface EmailIdRepository extends CrudRepository<EmailId, Integer> {
    EmailId findEmailIdByValue(String emailId);
}
