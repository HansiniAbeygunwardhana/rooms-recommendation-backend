package com.example.reservation.controller;

import com.example.reservation.dto.*;
import com.example.reservation.model.Contract;
import com.example.reservation.model.RoomType;
import com.example.reservation.repository.ContractRepository;
import com.example.reservation.repository.HotelRepository;
import com.example.reservation.repository.RoomTypeRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class BookingController {

    private final HotelRepository hotelRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final ContractRepository contractRepository;
    public BookingController(
            HotelRepository hotelRepository,
            RoomTypeRepository roomTypeRepository,
            ContractRepository contractRepository
    ) {
        this.hotelRepository = hotelRepository;
        this.roomTypeRepository = roomTypeRepository;
        this.contractRepository = contractRepository;
    }

    @PostMapping("search")
    public List<TestingDTO> showRecommendations(@RequestBody BookingDTO bookingDTO){

        RoomRecommendationsDTO roomRecommendations = new RoomRecommendationsDTO();
        List<TestingDTO> testings = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(bookingDTO.getCheckInDate());
        calendar.add(Calendar.DAY_OF_MONTH , bookingDTO.getNumberOfNights());
        Date checkOutDate = calendar.getTime();

        List<Contract> contracts = contractRepository.findByStartingDateLessThanEqualAndEndingDateGreaterThanEqualAndRoomTypesIsNotEmpty(bookingDTO.getCheckInDate() , checkOutDate);

        for (Contract contract:contracts) {
            TestingDTO testingDTO = new TestingDTO();
            List<BasicRoomTypeDTO> basicRoomTypeDTOS  = findSuitableRoomTypes(bookingDTO.getRooms() , contract);
            testingDTO.setBasicRoomTypes(basicRoomTypeDTOS);
            testingDTO.setContractId(contract.getId());
            testingDTO.setEndingDate(contract.getEndingDate());
            testingDTO.setStartingDate(contract.getStartingDate());
            testingDTO.setHotelName(contract.getHotel().getHotelName());

            testings.add(testingDTO);
        }

        return testings;
    }

    private List<BasicRoomTypeDTO> findSuitableRoomTypes(List<BookingRoomsDTO> bookingRoomsDTOS , Contract contract) {

        List<List<RoomType>> finalListOfSuitableRoomTypes = new ArrayList<>();


            for (BookingRoomsDTO bookingRoomsDTO : bookingRoomsDTOS) {
                List<RoomType> suitableRoomTypes = roomTypeRepository.findByNumberOfAdultsPerRoomGreaterThanEqualAndAvailableRoomsGreaterThanEqualAndContract(
                        bookingRoomsDTO.getNumberOfAdults(),
                        bookingRoomsDTO.getNumberOfRooms(),
                        contract
                );

                finalListOfSuitableRoomTypes.add(suitableRoomTypes);
        }

        return findCommonRoomTypes(finalListOfSuitableRoomTypes).stream()
                .map(this::convertToBasicRoomTypeDTO).toList();
    }

    private List<RoomType> findCommonRoomTypes(List<List<RoomType>> roomTypeLists) {
        if (roomTypeLists.isEmpty()) {
            return Collections.emptyList(); // Return an empty list if there are no input lists
        }

        List<RoomType> commonRoomTypes = roomTypeLists.get(0); // Initialize with the first list

        // Use stream operations to find common elements
        for (int i = 1; i < roomTypeLists.size(); i++) {
            List<RoomType> currentList = roomTypeLists.get(i);
            commonRoomTypes = commonRoomTypes.stream()
                    .filter(currentList::contains)
                    .collect(Collectors.toList());
        }



        return commonRoomTypes;
    }

    private BasicRoomTypeDTO convertToBasicRoomTypeDTO(RoomType roomType){

        BasicRoomTypeDTO basicRoomTypeDTO = new BasicRoomTypeDTO();
        basicRoomTypeDTO.setRoomType(roomType.getRoomType());
        basicRoomTypeDTO.setPrice(roomType.getMarkupPrice());
        basicRoomTypeDTO.setNumberOfAvailableRooms(roomType.getAvailableRooms());
        basicRoomTypeDTO.setId(roomType.getId());

        return  basicRoomTypeDTO;
    }


}

