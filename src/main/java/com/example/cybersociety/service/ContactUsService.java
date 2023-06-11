package com.example.cybersociety.service;

import com.example.cybersociety.dto.ContactUsRequestDTO;
import com.example.cybersociety.response.APIResponse;
import com.example.cybersociety.dto.ContactUsResponseDTO;
import com.example.cybersociety.entity.ContactUs;
import com.example.cybersociety.exception.ContactUsNotFoundException;
import com.example.cybersociety.exception.ContactUsServiceException;
import com.example.cybersociety.mapper.ContactUsMapper;
import com.example.cybersociety.repository.ContactUsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactUsService {

    private final ContactUsRepository contactUsRepository;


    public APIResponse<?> ContactUsConnection(ContactUsRequestDTO dto){
        try {
            log.info("ContactUsService :createNewContactUs execution started. ");
            ContactUs entity = ContactUsMapper.toEntity(dto);
            log.debug("ServiceFull: request parameters {}",ContactUsMapper.jsonAsString(dto));
            ContactUs save = contactUsRepository.save(entity);
        }catch (Exception e){
            log.error("Exception message:{}",e.getMessage());
            throw new ContactUsServiceException(e.getMessage());
        }
        return APIResponse.builder().message("OK").status(true).build();
    }

//    public APIResponse<?> updateContactUs(ContactUsRequestDTO dto, Long contactUsId) {
//        try {
//            log.info("ContactUsService: updateContactUs execution started.");
//            ContactUs existingContactUs = contactUsRepository.findById(contactUsId)
//                    .orElseThrow(() -> new ContactUsNotFoundException("ContactUs not found with id " + contactUsId));
//
//            ContactUs updatedContactUs = ContactUsMapper.toEntity(dto);
//            log.debug("ServiceFull: request parameters {}", ContactUsMapper.jsonAsString(dto));
//
//            existingContactUs.setName(updatedContactUs.getName());
//            existingContactUs.setCompany(updatedContactUs.getCompany());
//            existingContactUs.setMessage(updatedContactUs.getMessage());
//            existingContactUs.setPhoneNumber(updatedContactUs.getPhoneNumber());
//            ContactUs result = contactUsRepository.save(existingContactUs);
//        } catch (Exception e) {
//            log.error("Exception message: {}", e.getMessage());
//            throw new ContactUsServiceException(e.getMessage());
//        }
//        return APIResponse.builder().message("OK").status(true).build();
//    }

    public APIResponse<?> getContactUs(int page,int size){
        List<ContactUsResponseDTO> contactUsDTOList=null;
        try {
            log.info("getContactUs execution started");
            Pageable pageable = PageRequest.of(page, size);
            Page<ContactUs> all = contactUsRepository.findAll(pageable);
            if (!all.isEmpty()) {
                contactUsDTOList = all.stream().map(ContactUsMapper::toDTO).collect(Collectors.toList());
            } else {
                contactUsDTOList = Collections.emptyList();
            }
            log.debug("etContactUs retrieving contactus from database  {}", ContactUsMapper.jsonAsString(contactUsDTOList));
        }catch (Exception e){
            log.error("Exception occurred while retrieving contactus from database , Exception message {}",e.getMessage());
            throw new ContactUsServiceException(e.getMessage());
        }

        log.info("getContactUs execution ended");
        return APIResponse.builder().status(true).message("OK").results(contactUsDTOList).build();
    }

    public APIResponse<?> getContactUsById(Long id){
        ContactUsResponseDTO contactUsDTO=null;
       try {
           log.info("");
           ContactUs contactUs = contactUsRepository.findById(id).orElseThrow(() -> new ContactUsNotFoundException("ContactUs not found with id"));
           contactUsDTO= ContactUsMapper.toDTO(contactUs);
           log.debug("getContactUsById retrieving product from database for id {} {}",id,ContactUsMapper.jsonAsString(contactUsDTO));
       }catch (Exception e){
           log.error("Exception message {}",e.getMessage());
           throw new ContactUsServiceException("Exception occurred while fetch product from Database"+id);
       }
       log.info("getContactUsById execution ended ");
       return APIResponse.builder().status(true).message("OK").results(contactUsDTO).build();
    }
}
