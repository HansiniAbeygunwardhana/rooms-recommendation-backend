package com.example.reservation.dto;

import lombok.Data;

@Data
public class BasicRoomTypeDTO {

    private String roomType;
    private Double price ;
    private Integer numberOfAvailableRooms;
    private Long id;
}
