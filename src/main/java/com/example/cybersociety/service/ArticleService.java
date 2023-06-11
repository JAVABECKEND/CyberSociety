package com.example.cybersociety.service;

import com.example.cybersociety.dto.ArticleResponseDTO;
import com.example.cybersociety.dto.ArticlesRequestDTO;
import com.example.cybersociety.entity.Articles;
import com.example.cybersociety.entity.Media;
import com.example.cybersociety.exception.ArticlesServiceException;
import com.example.cybersociety.exception.MediaNotFoundException;
import com.example.cybersociety.exception.MultipartServiceException;
import com.example.cybersociety.mapper.ArticleMapper;
import com.example.cybersociety.repository.ArticlesRepository;
import com.example.cybersociety.repository.MediaRepository;
import com.example.cybersociety.response.APIResponse;
import com.example.cybersociety.response.ResponseFile;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final MediaRepository mediaRepository;
    private final ArticlesRepository articlesRepository;


    public APIResponse<?> saveArticles(ArticlesRequestDTO articlesRequestDTO){
        try {
            Media media = mediaRepository.findById (articlesRequestDTO.getArticlePhotoID ()).orElseThrow (() -> new MediaNotFoundException ("Media not found"));
            Articles entity = ArticleMapper.toEntity (articlesRequestDTO);
            entity.setArticlePhoto (media);
            articlesRepository.save (entity);
        }catch (Exception e){
            throw new MultipartServiceException (e.getMessage ());
        }
        return APIResponse.builder ().status (true).message ("OK").build ();
    }


    public  APIResponse<ArticleResponseDTO> getArticle(Integer id){
        ArticleResponseDTO responseDTO=null;
        try {
                Articles articles = articlesRepository.findById (id).orElseThrow (() -> new ArticlesServiceException ("Articles given by id not found"));
            responseDTO = ArticleMapper.toDTO (articles);
            }catch (Exception e){
                throw new ArticlesServiceException (e.getMessage ());
            }
        return APIResponse.<ArticleResponseDTO>builder().status (true).message ("OK").results (responseDTO).build();
    }


    public APIResponse<?> getArticleAll(int page ,int size){
        List<ArticleResponseDTO> articleResponseDTOS=null;
        try {
            PageRequest pageRequest = PageRequest.of (page, size);
            articleResponseDTOS = articlesRepository.findAll (pageRequest).stream ().map (ArticleMapper::toDTO).toList ();
        }catch (Exception e){
            throw new ArticlesServiceException (e.getMessage ());
        }
        return APIResponse.builder ().status (true).message ("OK").results (articleResponseDTOS).build ();
    }
    public Media getArticlePhoto(Long articleId){
        return mediaRepository.findById (articleId).orElseThrow (() -> new MultipartServiceException ("Article Photo given by id not found "));
    }

    public APIResponse<?> deleteArticle(Integer id){
        articlesRepository.deleteById (id);
        return APIResponse.builder ().message ("OK").status (true).build ();
    }
}
