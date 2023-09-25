package com.example.reservation.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoomRecommendationsDTO {

    private String hotelName;
    private List<BasicRoomTypeDTO> roomTypes;

}



