package com.example.reservation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.text.DecimalFormat;

@Data
@Entity
public class RoomType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomType;
    private Double price;
    private Double markupPrice;
    private Integer totalRooms;
    private Integer availableRooms;
    private Integer numberOfAdultsPerRoom;

    @JsonIgnore
    @ManyToOne
    private Contract contract;


    public void setPrice(Double price) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        this.price = Double.valueOf(decimalFormat.format(price));
        this.markupPrice = Double.valueOf(decimalFormat.format(price * 1.15)) ;
    }

    public void setTotalRooms(Integer totalRooms) {
        this.totalRooms = totalRooms;
        this.setAvailableRooms(totalRooms);
    }
}
