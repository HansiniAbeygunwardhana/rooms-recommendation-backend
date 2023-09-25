package com.example.reservation.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ContractDTO {

    private Date startingDate;
    private Date endingDate;
    private Long hotelId;
    private Long id;

}
