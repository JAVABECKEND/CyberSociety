package com.example.cybersociety.controller;
import com.example.cybersociety.dto.ArticleResponseDTO;
import com.example.cybersociety.dto.ArticlesRequestDTO;
import com.example.cybersociety.entity.Media;
import com.example.cybersociety.exception.MultipartServiceException;
import com.example.cybersociety.response.APIResponse;
import com.example.cybersociety.response.ResponseUploadFile;
import com.example.cybersociety.service.ArticleService;
import com.example.cybersociety.service.MultipartFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import java.io.IOException;

@RestController
@RequestMapping("/api/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final MultipartFileService multipartFileService;

    @PostMapping("/save")
    public ResponseEntity<?> saveArticle(@RequestBody ArticlesRequestDTO requestDTO){
        APIResponse<?> apiResponse = articleService.saveArticles (requestDTO);
        return ResponseEntity.status (apiResponse.getStatus ()?200:400).body (apiResponse);
    }

    @GetMapping("/get_article/{id}")
    public ResponseEntity<?> getArticle(@PathVariable("id") Integer id){
        APIResponse<ArticleResponseDTO> apiResponse = articleService.getArticle (id);
        return ResponseEntity.status (apiResponse.getStatus ()?200:400).body (apiResponse);
    }
    @GetMapping("/get_article/all")
    public ResponseEntity<?> getArticleAll(@RequestParam("page") int page,@RequestParam("size")int size){
        APIResponse<?> apiResponse = articleService.getArticleAll (page, size);
        return ResponseEntity.status (apiResponse.getStatus ()?200:400).body (apiResponse);
    }

    @PostMapping("/save_article_photo")
    public ResponseEntity<?> saveArticlePhoto(MultipartHttpServletRequest request){
        ResponseUploadFile<?> responseUploadFile=null;
        try {
            responseUploadFile = multipartFileService.saveMedia (request);
        } catch (IOException e) {
            throw new MultipartServiceException (e);
        }
        return ResponseEntity.ok (responseUploadFile);
    }

    @GetMapping("/article_photo/{id}")
    public ResponseEntity<?> getArticlePhoto(@PathVariable Long id){
        Media article = articleService.getArticlePhoto (id);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(article.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"media")
                .body(article.getBytes());
    }

    @DeleteMapping("/article_delete/{id}")
    public ResponseEntity<?> deleteParticle(@PathVariable Integer id){
        APIResponse<?> apiResponse = articleService.deleteArticle (id);
        return ResponseEntity.status (apiResponse.getStatus ()?200:400).body (apiResponse);
    }
}
