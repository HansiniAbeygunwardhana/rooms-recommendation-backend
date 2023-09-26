package com.example.reservation.controller;

import com.example.reservation.common.ApiResponse;
import com.example.reservation.dto.RoomTypeDTO;
import com.example.reservation.exception.ResourceNotFoundException;
import com.example.reservation.model.Contract;
import com.example.reservation.model.Hotel;
import com.example.reservation.model.RoomType;
import com.example.reservation.repository.ContractRepository;
import com.example.reservation.repository.HotelRepository;
import com.example.reservation.services.RoomTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class RoomTypeController {

    private static final Logger logger = LoggerFactory.getLogger(RoomTypeController.class);

    private final RoomTypeService roomTypeService;
    private final HotelRepository hotelRepository;
    private  final ContractRepository contractRepository;

    public RoomTypeController(RoomTypeService roomTypeService, HotelRepository hotelRepository , ContractRepository contractRepository) {
        this.roomTypeService = roomTypeService;
        this.hotelRepository = hotelRepository;
        this.contractRepository = contractRepository;

    }

    @GetMapping("/rooms")
    public List<RoomType> getAllRooms(){
        return roomTypeService.getALlRooms();
    }

    @PostMapping("/rooms")
    public ResponseEntity<ApiResponse> addRoom(@RequestBody RoomTypeDTO roomTypeDTO){

        RoomType roomType = new RoomType();
        roomType.setRoomType(roomTypeDTO.getRoomType());
        roomType.setPrice(roomTypeDTO.getPrice());
        roomType.setTotalRooms(roomTypeDTO.getAvailableRooms());
        roomType.setNumberOfAdultsPerRoom(roomTypeDTO.getNumberOfAdultsPerRoom());

        Contract contract = contractRepository.findById(roomTypeDTO.getContractId())
                .orElseThrow(() -> new ResourceNotFoundException("Contract not found with id" + roomTypeDTO.getContractId()));

        roomType.setContract(contract);

        roomTypeService.addRoomType(roomType);
        logger.info("Adding a new room types");
        return new ResponseEntity<>(new ApiResponse(true , "New roomType has been created"), HttpStatus.OK);
    }

    @GetMapping("/rooms/{id}")
    public List<RoomTypeDTO> getRoomTypeByContractId(@PathVariable Long id){

        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RoomType not found with id" + id));

        List<RoomType> roomTypes = roomTypeService.getRoomTypesByContract(contract);

        List<RoomTypeDTO> roomTypeDTOS = roomTypes
                .stream()
                .map(this::convertToRoomTypeDTO)
                .toList();

        logger.info("Getting rooms type by id : {}" , id);
        return roomTypeDTOS;
    }

    @PutMapping("/rooms/{id}")
    public ResponseEntity<ApiResponse> updateRoomType(@PathVariable Long id , @RequestBody RoomTypeDTO roomTypeDTO){

        try{
            RoomType roomType = roomTypeService.getRoomTypeById(id);

            roomType.setTotalRooms(roomTypeDTO.getAvailableRooms());
            roomType.setPrice(roomTypeDTO.getPrice());
            roomType.setRoomType(roomTypeDTO.getRoomType());
            roomType.setContract(roomType.getContract());

            roomTypeService.updateRoomType(roomType);
            return new ResponseEntity<>(new ApiResponse(true , "RoomType Updated"), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(true , "An Error Occurred"), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/rooms/id{id}")
    public RoomType getRoomTypeById(@PathVariable Long id) {

        RoomType roomType = roomTypeService.getRoomTypeById(id);
        logger.info("Getting room type by id : {}" , id);
        return roomType;
    }

    @DeleteMapping("/rooms/{id}")
    public ResponseEntity<ApiResponse> deleteRoomById(@PathVariable Long id){
        try {
            this.roomTypeService.deleteRoomTypeById(id);
            logger.info("Deleting room type with id : {}" , id);
            return new ResponseEntity<>(new ApiResponse(true , "Room Type Has Been Deleted"), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse(false , "An Error Occurred"), HttpStatus.BAD_REQUEST);
        }

    }

    private RoomTypeDTO convertToRoomTypeDTO (RoomType roomType){

        RoomTypeDTO roomTypeDTO = new RoomTypeDTO();
        roomTypeDTO.setAvailableRooms(roomType.getAvailableRooms());
        roomTypeDTO.setRoomType(roomType.getRoomType());
        roomTypeDTO.setPrice(roomType.getMarkupPrice());
        roomTypeDTO.setContractId(roomType.getContract().getId());
        roomTypeDTO.setNumberOfAdultsPerRoom(roomType.getNumberOfAdultsPerRoom());
        roomTypeDTO.setId(roomType.getId());


        return roomTypeDTO;
    }
}
