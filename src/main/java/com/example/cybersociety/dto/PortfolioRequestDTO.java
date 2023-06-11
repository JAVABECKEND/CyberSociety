package com.example.cybersociety.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
public class PortfolioRequestDTO {


    @NotBlank(message = "title cannot be null")
    private String header;

    @NotBlank(message = "description cannot be null")
    @Size(min = 50,max = 200)
    private String description;

    @NotBlank(message = "cannot be null url portfolio")
    private String url;
}
