package com.example.reservation.controller;

import com.example.reservation.common.ApiResponse;
import com.example.reservation.model.Customer;
import com.example.reservation.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin( origins = "*")
@RequestMapping("/api")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public List<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    @PostMapping("/customers/add")
    public ResponseEntity<ApiResponse> addCustomer(@RequestBody Customer customer) {
        customerService.addCustomer(customer);
        return new ResponseEntity<>(new ApiResponse(true , "A Customer has created") , HttpStatus.CREATED);
    }

    @DeleteMapping("/customers/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCustomerById(@PathVariable Long id){
        customerService.deleteCustomerById(id);
        return new ResponseEntity<>(new ApiResponse(true , "A customer has been deleted" ), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/customers/{id}")
    public Customer getCustomerById(@PathVariable Long id){
        return customerService.getCustomerById(id);
    }

}
