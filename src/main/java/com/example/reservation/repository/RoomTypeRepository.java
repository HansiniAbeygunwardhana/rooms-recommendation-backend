package com.example.reservation.repository;

import com.example.reservation.model.Contract;
import com.example.reservation.model.Hotel;
import com.example.reservation.model.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {

    List<RoomType> findByContract(Contract contract);

    List<RoomType> findByNumberOfAdultsPerRoomGreaterThanEqualAndAvailableRoomsGreaterThanEqual(Integer numberOfAdults, Integer numberOfRooms);

    List<RoomType> findByNumberOfAdultsPerRoomGreaterThanEqualAndAvailableRoomsGreaterThanEqualAndContract(Integer numberOfAdults, Integer numberOfRooms , Contract contract);
}
