package com.example.cybersociety.dto;

import com.example.cybersociety.entity.Media;
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
public class PortfolioResponseDTO {

    private Long id;
    private String header;
    private String description;
    private String url;
    private ResponseFile responseFile;
}
