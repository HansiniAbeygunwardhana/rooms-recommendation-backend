package com.example.reservation.services;

import com.example.reservation.exception.ResourceNotFoundException;
import com.example.reservation.model.Hotel;
import com.example.reservation.repository.HotelRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public List<Hotel> getAllHotels(){
        return hotelRepository.findAll();
    }

    public void addHotel(Hotel hotel){
        hotelRepository.save(hotel);
    }

    public void deleteHotelById(Long id){
        hotelRepository.deleteById(id);
    }

    public Hotel getHotelById(Long id){
        return hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel Not found with id" + id));
    }

    public void updateHotel (Hotel hotel){
        hotelRepository.save(hotel);
    }

    public Page<Hotel> getAllHotelsWithoutAddressWithPagination(Pageable pageable) {
        return hotelRepository.findAll(pageable);
    }

    public Page<Hotel> getAllHotels(Pageable pageable) {
        return hotelRepository.findAll(pageable);
    }
}
