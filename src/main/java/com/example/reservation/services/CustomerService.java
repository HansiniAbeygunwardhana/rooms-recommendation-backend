package com.example.reservation.services;

import com.example.reservation.model.Customer;
import com.example.reservation.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public  Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteCustomerById(Long id){
        customerRepository.deleteById(id);
    }

    public Customer getCustomerById(Long id){

        return customerRepository.findById(id).orElse(null);
    }
}
