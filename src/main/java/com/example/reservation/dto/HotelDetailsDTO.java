package com.example.reservation.dto;

import lombok.Data;

import java.util.List;

@Data
public class HotelDetailsDTO {

    private Long id;
    private String hotelName;
    private String hotelAddress;
    private List<ContractDTO> contracts;
}
