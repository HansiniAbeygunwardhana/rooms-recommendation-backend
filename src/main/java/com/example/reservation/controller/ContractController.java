package com.example.reservation.controller;

import com.example.reservation.common.ApiResponse;
import com.example.reservation.dto.ContractDTO;
import com.example.reservation.exception.ResourceNotFoundException;
import com.example.reservation.model.Contract;
import com.example.reservation.model.Hotel;
import com.example.reservation.repository.HotelRepository;
import com.example.reservation.services.ContractService;
import com.example.reservation.services.HotelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ContractController {

    private final ContractService contractService;

    private final HotelRepository hotelRepository;


    public ContractController(ContractService contractService , HotelRepository hotelRepository) {
        this.contractService = contractService;
        this.hotelRepository = hotelRepository;
    }

    @PostMapping("/contract")
    public ResponseEntity<ApiResponse> addContract(@RequestBody ContractDTO contractDTO){

        Contract contract = new Contract();
        contract.setEndingDate(contractDTO.getEndingDate());
        contract.setStartingDate(contractDTO.getEndingDate());

        Hotel hotel = hotelRepository.findById(contractDTO.getHotelId())
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id" + contractDTO.getHotelId()));
        contract.setHotel(hotel);

        contractService.addContract(contract);

        return new ResponseEntity<>(new ApiResponse(true , "A New Contract Added") , HttpStatus.CREATED);
    }

    @GetMapping("/contract")
    public List<Contract> getAllContracts(Contract contract){
       return this.contractService.getAllContracts();
    }

    @DeleteMapping("/contract/{id}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable Long id){
        try {
            this.contractService.deleteContractById(id);
            return new ResponseEntity<>(new ApiResponse(true,"Contract Successfully Deleted"),HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse(false,"An Error Occurred"),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/contract/hotel{id}")
    public List<ContractDTO> getByHotelId(@PathVariable Long id){

        List<Contract> contractList = this.contractService.getContractsByHotelId(id);
            return contractList.stream()
                    .map(this::convertToContractDTO)
                    .collect(Collectors.toList());
    }

    @GetMapping("/contract/{id}")
    public ContractDTO getById(@PathVariable Long id){

        Contract contract = this.contractService.getById(id);

        return convertToContractDTO(contract);
    }

    @PutMapping("/contract/{id}")
    public ResponseEntity<ApiResponse> updateContract(@PathVariable Long id, @RequestBody ContractDTO contractDTO) {
        try {
            Contract existingContract = contractService.getById(id);

            if (existingContract == null) {
                throw new ResourceNotFoundException("Contract not found with id" + id);
            }

            // Update the fields with the new values from the request body
            existingContract.setStartingDate(contractDTO.getStartingDate());
            existingContract.setEndingDate(contractDTO.getEndingDate());

            // If you want to update the associated hotel as well, you can do it here
            Hotel hotel = hotelRepository.findById(contractDTO.getHotelId())
                    .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id" + contractDTO.getHotelId()));
            existingContract.setHotel(hotel);

            contractService.updateContract(existingContract);

            return new ResponseEntity<>(new ApiResponse(true, "Contract Updated Successfully"), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse(false, "An Error Occurred"), HttpStatus.BAD_REQUEST);
        }
    }


    private ContractDTO convertToContractDTO(Contract contract){

        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setStartingDate(contract.getStartingDate());
        contractDTO.setEndingDate(contract.getEndingDate());
        contractDTO.setId(contract.getId());
        contractDTO.setHotelId(contract.getHotel().getId());

        return contractDTO;
    }

}
