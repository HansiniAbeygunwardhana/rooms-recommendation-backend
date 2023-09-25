package com.example.reservation.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hotelName;
    private String hotelAddress;

    @OneToMany(mappedBy = "hotel" , cascade = CascadeType.ALL)
    private List<Contract> contracts;

    public Hotel() {
    }
}
