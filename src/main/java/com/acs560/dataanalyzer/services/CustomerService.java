package com.acs560.dataanalyzer.services;

import java.util.List;
import java.util.Optional;

import com.acs560.dataanalyzer.models.Customer;

public interface CustomerService {
    Customer addCustomer(Customer customer);
    Optional<Customer> getCustomerById(Long id);
    Optional<Customer> getCustomerByEmail(String email);
    Iterable<Customer> getAllCustomers();
    Customer updateCustomer(Long id, Customer customer);
    void deleteCustomer(Long id);
    List<Customer> getCustomersByNameOrEmail(String value);

}