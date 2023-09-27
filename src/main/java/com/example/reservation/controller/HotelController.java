package com.example.reservation.controller;

import com.example.reservation.common.ApiResponse;
import com.example.reservation.dto.ContractDTO;
import com.example.reservation.dto.HotelDetailsDTO;
import com.example.reservation.dto.HotelNameDTO;
import com.example.reservation.exception.ResourceNotFoundException;
import com.example.reservation.model.Contract;
import com.example.reservation.model.Hotel;
import com.example.reservation.services.HotelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin (origins = "*")
@RequestMapping("/api")
public class HotelController {

    private final HotelService hotelService;

    private static  final Logger logger = LoggerFactory.getLogger(HotelController.class);

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/hotels")
    public List<HotelNameDTO> getAllHotelsWithoutAddress(){
        List<Hotel> hotels = hotelService.getAllHotels();

        List<HotelNameDTO> hotelNameDTOS = hotels.stream()
                .map(this::convertToHotelDTO)
                .collect(Collectors.toList());
        logger.info("Getting hotel names list");
        return hotelNameDTOS;
    }

    @GetMapping("/hotels/page")
    public Page<HotelNameDTO> getAllHotelsAsDTO(Pageable pageable) {
        Page<Hotel> hotelsPage = hotelService.getAllHotels(pageable);
        List<HotelNameDTO> hotelDTOList = hotelsPage
                .getContent()
                .stream()
                .map(this::convertToHotelDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(hotelDTOList, pageable, hotelsPage.getTotalElements());
    }


    @PostMapping("/hotels")
    public ResponseEntity<ApiResponse> addHotel(@RequestBody Hotel hotel){
        hotelService.addHotel(hotel);

        return new ResponseEntity<>(new ApiResponse(true  ,"A new Hotel has been Added"), HttpStatus.CREATED);
    }

    @PutMapping("hotels/{id}")
    public ResponseEntity<ApiResponse> updateContract(@PathVariable Long id, @RequestBody Hotel hotel) {
        try {
            Hotel existingHotel = hotelService.getHotelById(id);

            if (existingHotel == null) {
                throw new ResourceNotFoundException("Hotel not found with id" + id);
            }

            existingHotel.setHotelAddress(hotel.getHotelAddress());
            existingHotel.setHotelName(hotel.getHotelName());

            hotelService.updateHotel(existingHotel);

            return new ResponseEntity<>(new ApiResponse(true, "Hotel Updated Successfully"), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse(false, "An Error Occurred"), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/hotels/delete/{id}")
    public ResponseEntity<ApiResponse> deleteHotelById(@PathVariable Long id){
        hotelService.deleteHotelById(id);

        return new ResponseEntity<>(new ApiResponse(true , "Hotel has been deleted"), HttpStatus.OK);
    }

    @GetMapping("/hotels-all")
    public List<Hotel> getAllHotelDetails() {
        return hotelService.getAllHotels();
    }

    @GetMapping("hotels/{id}")
    public HotelDetailsDTO getHotelById(@PathVariable Long id) {
        Hotel hotel = hotelService.getHotelById(id);

        HotelDetailsDTO hotelDetailsDTO = new HotelDetailsDTO();
        hotelDetailsDTO.setId(hotel.getId());
        hotelDetailsDTO.setHotelAddress(hotel.getHotelAddress());
        hotelDetailsDTO.setHotelName(hotel.getHotelName());
        hotelDetailsDTO.setContracts(hotel.getContracts().stream()
                .map(this::convertToContractDTO)
                .collect(Collectors.toList())
        );
        return hotelDetailsDTO;
    }

    private HotelNameDTO convertToHotelDTO(Hotel hotel) {
        HotelNameDTO hotelNameDTO = new HotelNameDTO();
        hotelNameDTO.setId(hotel.getId());
        hotelNameDTO.setHotelName(hotel.getHotelName());
        return hotelNameDTO;
    }

    private ContractDTO convertToContractDTO (Contract contract){
        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setId(contract.getId());
        contractDTO.setEndingDate(contract.getEndingDate());
        contractDTO.setStartingDate(contract.getStartingDate());
        return contractDTO;
    }

}
