package com.example.reservation.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SearchDTO {

    private Long contractId;
    private Date startingDate;
    private Date endingDate;
    private String hotelName ;
    private List<BasicRoomTypeDTO> basicRoomTypes;
}
