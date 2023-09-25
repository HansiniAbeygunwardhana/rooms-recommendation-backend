package com.example.reservation.dto;

import lombok.Data;

@Data
public class RoomTypeDTO {

    private Long contractId;
    private String roomType;
    private Double price;
    private Integer availableRooms;
    private Integer numberOfAdultsPerRoom;
    private Long id;
}
