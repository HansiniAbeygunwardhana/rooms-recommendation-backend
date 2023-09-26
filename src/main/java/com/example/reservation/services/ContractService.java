package com.example.reservation.services;

import com.example.reservation.exception.ResourceNotFoundException;
import com.example.reservation.model.Contract;
import com.example.reservation.repository.ContractRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service///////
public class ContractService {

    private final ContractRepository contractRepository;

    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public List<Contract> getAllContracts(){
        return this.contractRepository.findAll();
    }

    public Page<Contract> getAllContracts(Pageable pageable){
        return this.contractRepository.findAll(pageable);
    }

    public void addContract(Contract contract){
        this.contractRepository.save(contract);
    }

    public void deleteContractById(Long id){
        this.contractRepository.deleteById(id);
    }

    public List<Contract> getContractsByHotelId(Long hotelId){
        return this.contractRepository.findByHotelIdOrderByEndingDateDesc(hotelId);
    }

    public Page<Contract> getContractbyPage( Long hotelId , Pageable pageable){
        return this.contractRepository.findByHotelIdOrderByEndingDateDesc(hotelId , pageable);
    }


    public Contract getById (Long id){
        return this.contractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contract Not found with id" + id));
    }

    public void updateContract(Contract updatedContract){
        contractRepository.save(updatedContract);
    }
}
