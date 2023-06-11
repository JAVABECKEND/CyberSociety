package com.example.cybersociety.controller;
import com.example.cybersociety.dto.PortfolioRequestDTO;
import com.example.cybersociety.entity.Media;
import com.example.cybersociety.response.APIResponse;
import com.example.cybersociety.response.ResponseUploadFile;
import com.example.cybersociety.service.PortfolioService;
import com.example.cybersociety.service.MultipartFileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;

@RestController
@RequestMapping("/api/portfolio/2023")
@RequiredArgsConstructor
public class PortfolioController {

    private final MultipartFileService multipartFileService;
    private final PortfolioService portfolioService;


    @PostMapping("/portfolio_save/{id}")
    public ResponseEntity<?> savePortfolio(@Valid @RequestBody PortfolioRequestDTO requestDTO, @PathVariable Long id){
        APIResponse<?> apiResponse = portfolioService.savePortfolio (requestDTO, id);
        return ResponseEntity.status(apiResponse.getStatus()? 200:400).body(apiResponse);
    }

    @GetMapping("/get_all_portfolio")
    public ResponseEntity<?> getAllPortfolio(@RequestParam("page") int page , @RequestParam("size") int size){
        APIResponse<?> allCV = portfolioService.getAllPortfolio (page, size);
        return ResponseEntity.status(allCV.getStatus()?200:400).body(allCV);
    }

    @GetMapping("/get_one_portfolio/{id}")
    public ResponseEntity<?> getOnePortfolio(@PathVariable Long id){
        APIResponse<?> oneCV = portfolioService.getOnePortfolio (id);
        return ResponseEntity.status(oneCV.getStatus()?200:400).body(oneCV);
    }

    @PutMapping("/portfolio_update/{id}/{mediaId}")
    public ResponseEntity<?> updatePortfolio(@Valid @RequestBody PortfolioRequestDTO requestDTO,
                                      @PathVariable("id") Long id,
                                      @PathVariable("mediaId")Long mediaId){
        APIResponse<?> apiResponse = portfolioService.updatePortfolio (requestDTO, id, mediaId);
        return ResponseEntity.status(apiResponse.getStatus()?200:400).body(apiResponse);
    }

    @DeleteMapping("/delete_portfolio/{id}")
    public ResponseEntity<?> deletePortfolio(@PathVariable Long id){
        APIResponse<?> apiResponse = portfolioService.deletePortfolio (id);
        return ResponseEntity.status(apiResponse.getStatus()?200:400).body(apiResponse);
    }

    @PostMapping("/save_media_file")
    public ResponseEntity<?>  saveMediaFile(MultipartHttpServletRequest request) throws IOException {
        ResponseUploadFile<?> responseUploadFile = multipartFileService.saveMedia(request);
        return ResponseEntity.status(responseUploadFile.getStatus()).body(responseUploadFile);
    }

    @GetMapping("/media_file/{id}")
    public ResponseEntity<?>  mediaFileSee(@PathVariable Long id) throws IOException {
      Media media= portfolioService.getOneMediaSee (id);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(media.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"media")
                .body(media.getBytes());
    }
}