package com.ebdms.backend.controller;

import com.ebdms.backend.dto.AddDonationRequest;
import com.ebdms.backend.dto.UpdateDonationRequest;
import com.ebdms.backend.model.DonationRequest;
import com.ebdms.backend.service.DonationRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donation-requests")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DonationRequestController {
    private final DonationRequestService donationRequestService;

    @PostMapping("/create")
    public ResponseEntity<?> createRequest(@RequestBody AddDonationRequest requestDto) {
        try {
            DonationRequest createdRequest = donationRequestService.createRequest(requestDto);
            return ResponseEntity.ok(createdRequest);


        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllRequests() {
        try {
               List<DonationRequest> requests= donationRequestService.getAllRequests();

            return ResponseEntity.ok(requests);

        } catch (RuntimeException e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getRequestById(@PathVariable Long id) {
        try {
            DonationRequest request= donationRequestService.getRequestById(id);

            return ResponseEntity.ok(request);

        } catch (RuntimeException e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRequest(@PathVariable Long id,@RequestBody UpdateDonationRequest updateDto){
        try {
            DonationRequest updatedRequest = donationRequestService.updateRequest( id,updateDto);
            return ResponseEntity.ok(updatedRequest);


        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }


    }


}
