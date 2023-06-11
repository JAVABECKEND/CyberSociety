package com.example.cybersociety.service;

import com.example.cybersociety.entity.Media;
import com.example.cybersociety.entity.Photo;
import com.example.cybersociety.repository.MediaRepository;
import com.example.cybersociety.repository.PhotoRepository;
import com.example.cybersociety.response.ResponseUploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
@Service
@RequiredArgsConstructor
public class MultipartFileService {

    private final MediaRepository mediaRepository;
    private final PhotoRepository photoRepository;


    @Transactional
    public ResponseUploadFile<?> saveMedia(MultipartHttpServletRequest request) throws IOException {
        MultipartFile file = request.getFile("media");
        if (file == null || file.isEmpty()) {
            return ResponseUploadFile.builder()
                    .status(400)
                    .resultError("file does not exist")
                    .build();
        }
        String contentType = file.getContentType();
        if (!isValidFileType(contentType)) {
            return ResponseUploadFile.builder()
                    .status(400)
                    .resultError("The file format is wrong")
                    .build();
        }
        Media media = new Media();
        media.setContentType(contentType);
        media.setName(file.getOriginalFilename());
        media.setSize(file.getSize());
        media.setBytes(file.getBytes());
        Media save = mediaRepository.save(media);
        return ResponseUploadFile.builder()
                .status(200)
                .resultSuccess(save.getId ())
                .build();
    }


    @Transactional
    public ResponseUploadFile<?> savePhoto(MultipartHttpServletRequest request) throws IOException {
        MultipartFile file = request.getFile("photo");
        if (file == null || file.isEmpty()) {
            return ResponseUploadFile.builder()
                    .status(400)
                    .resultError("file does not exist")
                    .build();
        }
        String contentType = file.getContentType();
        if (contentType == null || !(contentType.startsWith ("image") || contentType.startsWith ("jpeg"))) {
            return ResponseUploadFile.builder()
                    .status(400)
                    .resultError("The file format is wrong")
                    .build();
        }

        Photo photo = new Photo();
        photo.setContentType(contentType);
        photo.setName(file.getOriginalFilename());
        photo.setSize(file.getSize());
        photo.setBytes(file.getBytes());
        Photo save = photoRepository.save(photo);
        return ResponseUploadFile.builder()
                .status(200)
                .resultSuccess(save.getId ())
                .build();
    }



    private boolean isValidFileType(String contentType) {
        boolean ok=false;
        if (contentType == null) {
            return false;
        }
        String[] split = contentType.split("/");
        for (String next : split) {
            if (next.startsWith("video") || next.startsWith("mp4") || next.startsWith("image") || next.startsWith("jpeg")) {
                ok= true;
            }
        }
        return ok;
    }

}
