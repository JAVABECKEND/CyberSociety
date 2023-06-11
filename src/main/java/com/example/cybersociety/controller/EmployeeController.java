package com.example.cybersociety.controller;

import com.example.cybersociety.dto.EmployeeRequestDTO;
import com.example.cybersociety.entity.Media;
import com.example.cybersociety.entity.Photo;
import com.example.cybersociety.response.APIResponse;
import com.example.cybersociety.response.ResponseUploadFile;
import com.example.cybersociety.service.EmployeeService;
import com.example.cybersociety.service.MultipartFileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/api/employee/2023")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final MultipartFileService multipartFileService;

    @PostMapping("/employee_save/{photoId}")
    public ResponseEntity<?> saveEmployee(@Valid @RequestBody EmployeeRequestDTO requestDTO, @PathVariable("photoId") Integer id){
        APIResponse<?> apiResponse = employeeService.saveEmployee(requestDTO, id);
        return ResponseEntity.status(apiResponse.getStatus()? 200:400).body(apiResponse);
    }

    @GetMapping("/get_all_employee")
    public ResponseEntity<?> getEmployeeAll(@RequestParam("page") int page , @RequestParam("size") int size){
        APIResponse<?> all = employeeService.getEmployeeAll (page, size);
        return ResponseEntity.status(all.getStatus()?200:400).body(all);
    }
    @GetMapping("/get_one_employee/{id}")
    public ResponseEntity<?> getOneEmployee(@PathVariable Integer id){
        APIResponse<?> one = employeeService.getOneEmployee(id);
        return ResponseEntity.status(one.getStatus()?200:400).body(one);
    }

    @PutMapping("/update_employee/{id}/{photoId}")
    public ResponseEntity<?> updateEmployee(@Valid @RequestBody EmployeeRequestDTO requestDTO,
                                      @PathVariable("id") Integer id,
                                      @PathVariable("photoId")Integer photoId){
        APIResponse<?> apiResponse = employeeService.updateEmployee(requestDTO, id, photoId);
        return ResponseEntity.status(apiResponse.getStatus()?200:400).body(apiResponse);
    }

    @DeleteMapping("/delete_employee/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Integer id){
        APIResponse<?> apiResponse = employeeService.deleteEmployee(id);
        return ResponseEntity.status(apiResponse.getStatus()?200:400).body(apiResponse);
    }

    @PostMapping("/save_photo_file")
    public ResponseEntity<?>  savePhoto(MultipartHttpServletRequest request) throws IOException {
        ResponseUploadFile<?> responseUploadFile = multipartFileService.savePhoto(request);
        return ResponseEntity.status(responseUploadFile.getStatus()).body(responseUploadFile);
    }

    @GetMapping("/photo_file/{id}")
    public ResponseEntity<?>  photoFileSee(@PathVariable Integer id) throws IOException {
        Photo media= employeeService.getOnePhoto (id);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(media.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"photo")
                .body(media.getBytes());
    }


    @GetMapping("/photo_files")
    public ResponseEntity<?> getAllPhotoFiles() throws IOException {
        List<String> morePhoto = employeeService.getMorePhoto ();
        return ResponseEntity.ok()
                .body(morePhoto);
    }




}
