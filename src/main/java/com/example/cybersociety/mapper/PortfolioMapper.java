package com.example.cybersociety.mapper;

import com.example.cybersociety.dto.PortfolioRequestDTO;
import com.example.cybersociety.dto.PortfolioResponseDTO;
import com.example.cybersociety.entity.Media;
import com.example.cybersociety.entity.Photo;
import com.example.cybersociety.entity.Portfolio;
import com.example.cybersociety.response.ResponseFile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class PortfolioMapper {

    public static PortfolioResponseDTO toDTO(Portfolio cv){
        return PortfolioResponseDTO.builder()
                .id(cv.getId())
                .description(cv.getDescription())
                .header(cv.getHeader())
                .url(cv.getUrl())
                .responseFile (responseFileMedia (cv.getMedia ()))
                .build();
    }

    public static Portfolio toENTITY(PortfolioRequestDTO requestDTO){
        return Portfolio.builder()
                .description(requestDTO.getDescription())
                .header(requestDTO.getHeader())
                .url(requestDTO.getUrl())
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

    private  static ResponseFile responseFileMedia(Media photo){
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/portfolio/2023/media_file/")
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
