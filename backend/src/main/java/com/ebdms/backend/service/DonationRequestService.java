package com.ebdms.backend.service;


import com.ebdms.backend.dto.AddDonationRequest;
import com.ebdms.backend.dto.UpdateDonationRequest;
import com.ebdms.backend.enums.RequestStatus;
import com.ebdms.backend.model.BloodBank;
import com.ebdms.backend.model.DonationRequest;
import com.ebdms.backend.repository.BloodBankRepository;
import com.ebdms.backend.repository.DonationRequestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DonationRequestService {
    private final DonationRequestRepository donationRequestRepository;
    private final BloodBankRepository bloodBankRepository;

    @Transactional
    public DonationRequest createRequest(AddDonationRequest requestDto) {

        //  (Validation)
        BloodBank requestingBank = bloodBankRepository.findById(requestDto.getBloodBankId())
                .orElseThrow(() -> new RuntimeException("Blood Bank not found with ID: " + requestDto.getBloodBankId()));
        DonationRequest newRequest = DonationRequest.builder()
                .patientName(requestDto.getPatientName())
                .patientAge(requestDto.getPatientAge())
                .medicalStatus(requestDto.getMedicalStatus())
                .bloodType(requestDto.getBloodType())
                .bagsNeeded(requestDto.getBagsNeeded())
                .severity(requestDto.getSeverity())
                .caseDetails(requestDto.getCaseDetails())
                .city(requestDto.getCity())
                .detailedAddress(requestDto.getDetailedAddress())
                .notes(requestDto.getNotes())
                .bloodBank(requestingBank)
                .status(RequestStatus.PENDING)
                .bagsCollected(0)
                .build();


        return donationRequestRepository.save(newRequest);
    }
    public List<DonationRequest> getAllRequests() {
      return   donationRequestRepository.findAll();
    }

    public DonationRequest getRequestById(long id){

     return donationRequestRepository.findById(id)
             .orElseThrow(()->new RuntimeException("Donation request not found with ID: " + id)) ;
    }
     @Transactional
     public DonationRequest updateRequest(Long id, UpdateDonationRequest updateDto) {
     DonationRequest existingRequest = getRequestById (id);
     existingRequest.setPatientName(updateDto.getPatientName());
     existingRequest.setPatientAge(updateDto.getPatientAge());
     existingRequest.setMedicalStatus(updateDto.getMedicalStatus());
     existingRequest.setBagsNeeded(updateDto.getBagsNeeded());
     existingRequest.setBloodType(updateDto.getBloodType());
     existingRequest.setCaseDetails(updateDto.getCaseDetails());
     existingRequest.setSeverity(updateDto.getSeverity());
     existingRequest.setCity(updateDto.getCity());
     existingRequest.setDetailedAddress(updateDto.getDetailedAddress());
     existingRequest.setNotes(updateDto.getNotes());

         return donationRequestRepository.save(existingRequest);

     }
}


