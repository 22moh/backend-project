package com.ebdms.backend.controller;

import com.ebdms.backend.dto.AddBloodBankRequest;
import com.ebdms.backend.dto.UpdateBloodBankRequest;
import com.ebdms.backend.model.BloodBank;
import com.ebdms.backend.service.BloodBankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/blood-banks") // ده رابط خاص بالآدمن
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BloodBankController {

    private final BloodBankService bloodBankService;

    // API لإضافة بنك دم جديد
    // الرابط: POST http://localhost:8080/api/admin/blood-banks/add
    @PostMapping("/add")
    public ResponseEntity<?> addBloodBank(@RequestBody AddBloodBankRequest request) {
        try {
            BloodBank createdBank = bloodBankService.addBloodBank(request);
            return ResponseEntity.ok(createdBank);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/all")
    public ResponseEntity<?> getBloodBanks(){
    try{
       List<BloodBank>  allBloodBanks= bloodBankService.getAllBloodBanks();
        return ResponseEntity.ok(allBloodBanks);
       }catch (RuntimeException e){
        return ResponseEntity.badRequest().body(e.getMessage());
       }

    }
   @PutMapping("/{id}")
    public ResponseEntity<?> updateBloodBank(@PathVariable Long id,
                                             @RequestBody UpdateBloodBankRequest request)
   {
        try {
            BloodBank updatedBloodBank= bloodBankService.updateBloodBank(id,request);
            return ResponseEntity.ok(updatedBloodBank);
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

   }

    @PatchMapping("/{id}/block")
    public ResponseEntity<?> blockBloodBank(@PathVariable Long id) {
        try {

            BloodBank blockedBank = bloodBankService.blockBloodBank(id);

            return ResponseEntity.ok(blockedBank);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
   

}
