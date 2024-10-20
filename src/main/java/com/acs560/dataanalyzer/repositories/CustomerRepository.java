package com.acs560.dataanalyzer.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import com.acs560.dataanalyzer.models.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
	List<Customer> findByNameContainingOrEmailContaining(String value, String value2);

}