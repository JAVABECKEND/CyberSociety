package com.example.cybersociety.mapper;

import com.example.cybersociety.dto.ContactUsRequestDTO;
import com.example.cybersociety.dto.ContactUsResponseDTO;
import com.example.cybersociety.entity.ContactUs;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;

public class ContactUsMapper {

    public static ContactUsResponseDTO toDTO(ContactUs contactUs){
        return ContactUsResponseDTO.builder()
                .id(contactUs.getId())
                .company(contactUs.getCompany())
                .localDate(String.valueOf (contactUs.getLocalDate()))
                .message(contactUs.getMessage())
                .name(contactUs.getName())
                .email (contactUs.getEmail ())
                .phoneNumber (contactUs.getPhoneNumber ())
                .build();
    }

    public static ContactUs toEntity(ContactUsRequestDTO requestDTO){
        return ContactUs.builder()
                .name(requestDTO.getName())
                .company(requestDTO.getCompany())
                .message(requestDTO.getMessage())
                .localDate(LocalDate.now ())
                .email (requestDTO.getEmail ())
                .phoneNumber (requestDTO.getPhoneNumber ())
                .build();
    }

    public static String jsonAsString(Object obj){
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
