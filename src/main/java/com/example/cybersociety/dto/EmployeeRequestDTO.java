package com.example.cybersociety.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
public class EmployeeRequestDTO {

    @NotBlank(message = "fulName cannot be null")
    private String fulName;
    @NotBlank(message = "age cannot be null")
    private String age;
    @NotBlank(message = "linkDinFullName cannot be null")
    private String linkDinFullName;
}
