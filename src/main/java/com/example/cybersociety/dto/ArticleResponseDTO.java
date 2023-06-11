package com.example.cybersociety.dto;

import com.example.cybersociety.response.ResponseFile;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleResponseDTO {
    private Integer id;
    private String title;
    private String content;
    private String date;
    private ResponseFile responseFile;
}
