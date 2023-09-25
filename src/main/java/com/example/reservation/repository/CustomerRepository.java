package com.example.reservation.repository;

import com.example.reservation.model.Contract;
import com.example.reservation.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer , Long> {


}
