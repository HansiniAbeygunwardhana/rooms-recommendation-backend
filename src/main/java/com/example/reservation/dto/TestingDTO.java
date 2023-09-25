package com.example.reservation.dto;

import com.example.reservation.model.RoomType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Data
public class TestingDTO {

    private Long contractId;
    private Date startingDate;
    private Date endingDate;
    private String hotelName ;
    private List<BasicRoomTypeDTO> basicRoomTypes;
}
