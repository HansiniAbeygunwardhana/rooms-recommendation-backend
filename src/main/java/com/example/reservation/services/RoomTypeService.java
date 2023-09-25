package com.example.reservation.services;

import com.example.reservation.model.Contract;
import com.example.reservation.model.RoomType;
import com.example.reservation.repository.RoomTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomTypeService {

    private final RoomTypeRepository roomTypeRepository;

    public RoomTypeService(RoomTypeRepository roomTypeRepository) {
        this.roomTypeRepository = roomTypeRepository;
    }

    public List<RoomType> getALlRooms(){
        return this.roomTypeRepository.findAll();
    }

    public void addRoomType(RoomType roomType){
        this.roomTypeRepository.save(roomType);
    }

    public void deleteRoomTypeById(Long id){
        this.roomTypeRepository.deleteById(id);
    }

    public RoomType getRoomTypeById(Long id){
        return roomTypeRepository.findById(id).orElse(null);
    }

    public void updateRoomType(RoomType roomType){
        roomTypeRepository.save(roomType);
    }

    public void updateAvailableRooms(Long id , Integer availableRooms){
        RoomType roomType = roomTypeRepository.findById(id).orElse(null);

        assert roomType != null;
        if (availableRooms < roomType.getAvailableRooms()){
            roomType.setAvailableRooms(availableRooms);

        }

    }

    public List<RoomType> getRoomTypesByContract (Contract contract){
        return roomTypeRepository.findByContract(contract);
    }

}
