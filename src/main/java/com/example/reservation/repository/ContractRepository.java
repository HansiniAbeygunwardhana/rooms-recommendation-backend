package com.example.reservation.repository;

import com.example.reservation.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract , Long> {

    List<Contract> findByStartingDateLessThanEqualAndEndingDateGreaterThanEqualAndRoomTypesIsNotEmpty(
            Date startingDate, Date endingDate);

    List<Contract> findByHotelId (Long hotelId);
}
