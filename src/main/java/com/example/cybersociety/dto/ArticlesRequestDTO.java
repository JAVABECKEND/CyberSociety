package com.example.cybersociety.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class ArticlesRequestDTO {
    @NotBlank(message = "cannot be null title ")
    private String title;
    @NotBlank(message = "cannot be null content ")
    private String content;
    @NotBlank(message = "cannot be null articlePhotoID ")
    private Long articlePhotoID;
    @NotBlank(message = "cannot be null date ")
    private String date;
}
