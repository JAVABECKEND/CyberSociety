package com.example.cybersociety.mapper;

import com.example.cybersociety.dto.EmployeeRequestDTO;
import com.example.cybersociety.dto.EmployeeResponseDTO;
import com.example.cybersociety.entity.Employee;
import com.example.cybersociety.entity.Photo;
import com.example.cybersociety.response.ResponseFile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.time.LocalDate;

public class EmployeeMapper {

    public static EmployeeResponseDTO toDTO(Employee employee){
        return EmployeeResponseDTO.builder()
                .id(employee.getId())
                .age(String.valueOf (employee.getAge()))
                .fulName(employee.getFulName())
                .linkDinFullName(employee.getLinkDinFullName())
                .responseFile (responseFile (employee.getPhoto ()))
        .build();
    }

    public static Employee toENTITY(EmployeeRequestDTO requestDTO){
        return Employee.builder()
                .age(LocalDate.parse (requestDTO.getAge()))
                .fulName(requestDTO.getFulName())
                .linkDinFullName(requestDTO.getLinkDinFullName())
                .build();
    }



    public static String jsonAsString(Object o){
        try {
            return new ObjectMapper().writeValueAsString(o);
        } catch (JsonProcessingException e) {
           e.printStackTrace();
        }
        return null;
    }
    private  static ResponseFile responseFile(Photo photo){
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/employee/2023/photo_file/")
                .path(String.valueOf (photo.getId ()))
                .toUriString();

        return ResponseFile.builder ()
                .size (photo.getSize ())
                .type (photo.getContentType ())
                .url (fileDownloadUri)
                .name (photo.getName ())
                .build ();
    }
}
