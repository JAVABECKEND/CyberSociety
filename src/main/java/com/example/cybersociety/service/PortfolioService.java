package com.example.cybersociety.service;

import com.example.cybersociety.dto.PortfolioRequestDTO;
import com.example.cybersociety.dto.PortfolioResponseDTO;
import com.example.cybersociety.entity.Portfolio;
import com.example.cybersociety.entity.Media;
import com.example.cybersociety.exception.MultipartServiceException;
import com.example.cybersociety.exception.PortfolioNotFoundException;
import com.example.cybersociety.exception.PortfolioServiceException;
import com.example.cybersociety.exception.MediaNotFoundException;
import com.example.cybersociety.mapper.PortfolioMapper;
import com.example.cybersociety.repository.PortfolioRepository;
import com.example.cybersociety.repository.MediaRepository;
import com.example.cybersociety.response.APIResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PortfolioService {

   private final PortfolioRepository portfolioRepository;
   private final MediaRepository mediaRepository;


   public APIResponse<?> savePortfolio(PortfolioRequestDTO requestDTO, Long mediaId){
       try {
           log.info("PortfolioService: creating a new cv");
           Portfolio entity = PortfolioMapper.toENTITY(requestDTO);
           log.debug("PortfolioService:createNewCV request parameters {}", PortfolioMapper.jsonAsString(requestDTO));
           Media media = mediaRepository.findById(mediaId).orElseThrow(() -> new MultipartServiceException ("media not found with id  " + mediaId));
           entity.setMedia(media);
           portfolioRepository.save(entity);
       }catch (Exception e){
           log.error("PortfolioService:Exception occurred while persisting Portfolio to database , Exception message {}",e.getMessage());
           throw new PortfolioServiceException (e.getMessage ());
       }
       log.info("PortfolioService:createNewPortfolio execution ended.");
       return APIResponse.builder().status(true).message("OK").build();
   }


    public APIResponse<?> updatePortfolio(PortfolioRequestDTO requestDTO, Long cvId, Long mediaId) {
        try {
            log.info("PortfolioService: updating Portfolio");
            Portfolio existingCV = portfolioRepository.findById(cvId)
                    .orElseThrow(() -> new PortfolioNotFoundException ("Portfolio not found with id "));

            Portfolio updatedCV = PortfolioMapper.toENTITY(requestDTO);
            log.debug("PortfolioService:updatePortfolio request parameters {}", PortfolioMapper.jsonAsString(requestDTO));

            if (mediaId == null) {
                throw new PortfolioServiceException ("It is not possible to enter null mediaId to update Portfolio");
            }

            Media media = mediaRepository.findById(mediaId)
                    .orElseThrow(() -> new MediaNotFoundException("Media not found with id " + mediaId));
            existingCV.setHeader(updatedCV.getHeader());
            existingCV.setDescription(updatedCV.getDescription());
            existingCV.setUrl(updatedCV.getUrl());
            existingCV.setMedia(media);
            portfolioRepository.save(existingCV);
        } catch (Exception e) {
            log.error("PortfolioService: Exception occurred while updating Portfolio, Exception message {}", e.getMessage());
            throw new PortfolioServiceException ("Exception occurred while updating Portfolio");
        }
        log.info("PortfolioService: updatePortfolio execution ended.");
        return APIResponse.builder().status(true).message("OK").build();
    }


    public APIResponse<?> getAllPortfolio(int page,int size){
        List<PortfolioResponseDTO> responseDTOList=null;
       try {
           log.info("PortfolioService:getPortfolio all execution started");
           Pageable pageable= PageRequest.of(page, size);
           Page<Portfolio> all = portfolioRepository.findAll(pageable);
           if (!all.isEmpty()) {
               responseDTOList=all.stream().map(PortfolioMapper::toDTO).collect(Collectors.toList());
           }else {
               responseDTOList= Collections.emptyList();
           }

           log.debug("PortfolioService:PortfolioService:getProducts retrieving Portfolios from database  {}", PortfolioMapper.jsonAsString(responseDTOList));
       }catch (Exception e){
           log.error("PortfolioService:Exception occurred while retrieving Portfolios from database , Exception message {}",e.getMessage());
           throw new PortfolioServiceException ("");
       }
       log.info("PortfolioService: getPortfolio all execution ended.");
       return APIResponse.builder().status(true).message("OK").results(responseDTOList).build();
   }

   public APIResponse<?> getOnePortfolio(Long id){
       PortfolioResponseDTO cvResponseDTO=null;
       try{
           log.info("PortfolioService: getPortfolio one execution started");
           Portfolio cv = portfolioRepository.findById(id).orElseThrow(() -> new PortfolioNotFoundException ("Portfolio not found with id "));
           cvResponseDTO= PortfolioMapper.toDTO(cv);
           log.debug("PortfolioService:getProductById retrieving product from database for id {} {}",id, PortfolioMapper.jsonAsString(cvResponseDTO));
       }catch (Exception e){
           log.error("PortfolioService: Exception occurred while retrieving Portfolio {} from database , Exception message {}",id,e.getMessage());
           throw new PortfolioServiceException ("PortfolioService: Exception occurred while fetch Portfolio from Database");
       }
       log.info("PortfolioService: get Portfolio one execution ended");
       return APIResponse.builder().status(true).message("OK").results(cvResponseDTO).build();
   }

   public APIResponse<?> deletePortfolio(Long id){
       log.info("PortfolioService: delete portfolio execution started");
       try {
           portfolioRepository.deleteById(id);
       }catch (Exception e){
           log.error("PortfolioService: Could not delete portfolio at given {}  {}",id,e.getMessage());
       }
       log.info("PortfolioService: delete Portfolio ");
       return APIResponse.builder().message("portfolio at given id deleted").status(true).build();
   }

    public Media getOneMediaSee(Long id){
        Media media=null;
        try{
            log.info("PortfolioService: getMedia one execution started");
           media= mediaRepository.findById (id).orElseThrow (() -> new MultipartServiceException ("The reference for the entered id was not found"));
        }catch (Exception e){
            log.error("PortfolioService: Exception occurred while retrieving media {} from database , Exception message {}",id,e.getMessage());
            throw new PortfolioServiceException ("PortfolioService: Exception occurred while fetch media from Database");
        }
        log.info("PortfolioService: get Portfolio one execution ended");
        return media;
    }
}
