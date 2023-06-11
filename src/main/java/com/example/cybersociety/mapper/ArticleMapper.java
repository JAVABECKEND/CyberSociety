package com.example.cybersociety.mapper;

import com.example.cybersociety.dto.ArticleResponseDTO;
import com.example.cybersociety.dto.ArticlesRequestDTO;
import com.example.cybersociety.entity.Articles;
import com.example.cybersociety.entity.Media;
import com.example.cybersociety.response.ResponseFile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.time.LocalDate;


public class ArticleMapper {

    public static ArticleResponseDTO toDTO(Articles articles){
        return ArticleResponseDTO.builder()
                .id(articles.getId())
                .content (articles.getContent ())
                .title (articles.getTitle ())
                .date (String.valueOf (articles.getLocalDate ()))
                .responseFile (responseFileMedia (articles.getArticlePhoto ()))
                .build();
    }

    public static Articles toEntity(ArticlesRequestDTO requestDTO){
        return Articles.builder()
                .title (requestDTO.getTitle ())
                .content (requestDTO.getContent ())
                .localDate (LocalDate.parse (requestDTO.getDate ()))
                .build();
    }

    public static String jsonAsString(Object obj){
        try {
            return new ObjectMapper ().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private  static ResponseFile responseFileMedia(Media photo){
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/article/article_photo/")
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
