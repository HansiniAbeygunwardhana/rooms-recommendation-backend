package com.example.reservation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date endingDate;
    private Date startingDate;

    @ManyToOne
    @JsonIgnore
    private Hotel hotel;

    @OneToMany(mappedBy = "contract" , cascade = CascadeType.ALL)
    private List<RoomType> roomTypes;
}
