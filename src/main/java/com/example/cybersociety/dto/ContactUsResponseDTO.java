package com.example.cybersociety.dto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContactUsResponseDTO {

    private Long id;
    private String name;
    private String company;
    private String phoneNumber;
    private String message;
    private String email;
    private String localDate;
}
