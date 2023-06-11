package com.example.cybersociety.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class ContactUsRequestDTO {


    @NotBlank(message = "name cannot be null")
    @Size(max = 20)
    private String name;
    @NotBlank(message = "company name cannot be null")
    @Size(max = 20)
    private String company;
    @NotBlank(message = "phoneNumber cannot be null")
    private String phoneNumber;
    @NotBlank(message = "email cannot be null")
    private String email;
    @Size(max = 2000,min = 10)
    private String message;
}
