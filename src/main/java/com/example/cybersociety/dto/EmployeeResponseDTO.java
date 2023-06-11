package com.example.cybersociety.dto;

import com.example.cybersociety.response.ResponseFile;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeResponseDTO {

    private Integer id;
    private String fulName;
    private String age;
    private String linkDinFullName;
    private ResponseFile responseFile;
}
