package com.example.reservation.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BookingDTO {

    private Date checkInDate;
    private Integer numberOfNights;
    private List<BookingRoomsDTO> rooms;
}

