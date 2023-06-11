package com.example.cybersociety.controller;

import com.example.cybersociety.dto.ContactUsRequestDTO;
import com.example.cybersociety.response.APIResponse;
import com.example.cybersociety.service.ContactUsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contactus/2023")
@RequiredArgsConstructor
public class ContactUsController {

    private final ContactUsService usService;

    @PostMapping("/connection")
    public ResponseEntity<?> ContactUs(@Valid @RequestBody ContactUsRequestDTO dto){
        APIResponse<?> apiResponse = usService.ContactUsConnection(dto);
        return ResponseEntity.status(apiResponse.getStatus()?200:400).body(apiResponse);
    }

    @GetMapping("/contact_us/page_size")
    public ResponseEntity<?> getContactUs(@RequestParam("page")int page,
                                          @RequestParam("size")int size){
        APIResponse<?> contactUs = usService.getContactUs(page, size);
        return ResponseEntity.status(contactUs.getStatus()?200:400).body(contactUs);
    }


    @GetMapping("/contact_us/{id}")
    public ResponseEntity<?> getContactUsById(@PathVariable Long id){
        APIResponse<?> contactUs = usService.getContactUsById(id);
        return ResponseEntity.status(contactUs.getStatus()?200:400).body(contactUs);
    }


}
