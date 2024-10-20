package com.acs560.dataanalyzer.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acs560.dataanalyzer.models.Customer;
import com.acs560.dataanalyzer.repositories.CustomerRepository;
import com.acs560.dataanalyzer.services.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	 @Autowired
	    private CustomerRepository customerRepository;

	    @Override
	    public Customer addCustomer(Customer customer) {
	        return customerRepository.save(customer);
	    }

	    @Override
	    public Optional<Customer> getCustomerById(Long id) {
	        return customerRepository.findById(id);
	    }

	    @Override
	    public List<Customer> getCustomersByNameOrEmail(String value) {
	        // Assuming you have a repository or some storage mechanism to fetch customers
	        return customerRepository.findByNameContainingOrEmailContaining(value, value);
	    }


	    @Override
	    public Iterable<Customer> getAllCustomers() {
	        return customerRepository.findAll();
	    }

	    @Override
	    public Customer updateCustomer(Long id, Customer customer) {
	        if (customerRepository.existsById(id)) {
	            customer.setCustomerId(id);
	            return customerRepository.save(customer);
	        }
	        return null; // Or throw a custom exception
	    }

	    @Override
	    public void deleteCustomer(Long id) {
	        if (customerRepository.existsById(id)) {
	            customerRepository.deleteById(id);
	        } else {
	            // Handle case where customer does not exist
	            throw new RuntimeException("Customer not found");
	        }
	    }

		@Override
		public Optional<Customer> getCustomerByEmail(String email) {
			// TODO Auto-generated method stub
			return customerRepository.findByEmail(email);
		}

  
}
