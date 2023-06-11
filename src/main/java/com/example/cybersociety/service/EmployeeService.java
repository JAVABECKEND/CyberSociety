package com.example.cybersociety.service;

import com.example.cybersociety.dto.EmployeeRequestDTO;
import com.example.cybersociety.dto.EmployeeResponseDTO;
import com.example.cybersociety.entity.Employee;
import com.example.cybersociety.entity.Photo;
import com.example.cybersociety.exception.EmployeeServiceException;
import com.example.cybersociety.exception.MultipartServiceException;
import com.example.cybersociety.exception.PhotoNotFoundException;
import com.example.cybersociety.exception.UserNotfoundException;
import com.example.cybersociety.mapper.EmployeeMapper;
import com.example.cybersociety.repository.EmployeeRepository;
import com.example.cybersociety.repository.PhotoRepository;
import com.example.cybersociety.response.APIResponse;
import com.example.cybersociety.response.ResponseFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PhotoRepository photoRepository;


    public APIResponse<?>  saveEmployee(EmployeeRequestDTO dto,Integer photoId){
        try {
            log.info("EmployeeService: createNewContactUs execution started.");
            Employee entity = EmployeeMapper.toENTITY(dto);
            log.info("PhotoId {} by MultipartService from Photo get",photoId);
            Photo photo = photoRepository.findById(photoId).orElseThrow(() -> new MultipartServiceException ("photo not found with id " + photoId));
            entity.setPhoto(photo);
            Employee resualt = employeeRepository.save(entity);
            EmployeeResponseDTO responseDTO = EmployeeMapper.toDTO(resualt);
            log.debug("EmployeeService:createNewEmployee received response from Database {}",EmployeeMapper.jsonAsString(responseDTO));
        }catch (Exception e){
            log.error("Exception occurred while persisting employee to database , Exception message {}", e.getMessage());
            throw new EmployeeServiceException(e.getMessage ());
        }
        log.info("EmployeeService:saveEmployee execution ended.");
        return APIResponse.builder().status(true).message("OK").build();
    }



    public APIResponse<?> updateEmployee(EmployeeRequestDTO dto, Integer employeeId, Integer photoId) {
        try {
            log.info("EmployeeService: updateEmployee execution started.");
            Employee existingEmployee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new UserNotfoundException("Employee not found with id " + employeeId));

            Employee updatedEmployee = EmployeeMapper.toENTITY(dto);
            updatedEmployee.setId(existingEmployee.getId());
            log.info("PhotoId {} by MultipartService from Photo get", photoId);

            if (photoId == null) {
               throw new EmployeeServiceException("photo id cannot be null");
            }

            try {
                Photo photo = photoRepository.findById(photoId)
                        .orElseThrow(() -> new MultipartServiceException("Photo not found with id " + photoId));

                updatedEmployee.setPhoto(photo);
                Employee result = employeeRepository.save(updatedEmployee);
                EmployeeResponseDTO responseDTO = EmployeeMapper.toDTO(result);
                log.debug("EmployeeService:updateEmployee received response from Database {}", EmployeeMapper.jsonAsString(responseDTO));
            } catch (Exception e) {
                log.error("Exception message: {}", e.getMessage());
                throw new MultipartServiceException("");
            }
        } catch (Exception e) {
            log.error("Exception occurred while updating employee in the database, Exception message {}", e.getMessage());
            throw new EmployeeServiceException("Exception occurred while updating an employee");
        }
        log.info("EmployeeService:updateEmployee execution ended.");
        return APIResponse.builder().status(true).message("OK").build();
    }


    public APIResponse<?> getEmployeeAll(int page,int size){
        List<EmployeeResponseDTO> responseDTOList =null;
        Pageable pageable= PageRequest.of(page, size);
        try{
            log.info("EmployeeService: getEmployee execution started");
            Page<Employee> employees = employeeRepository.findAll(pageable);
            if (!employees.isEmpty()) {
                responseDTOList= employees.stream()
                        .map(EmployeeMapper::toDTO)
                        .collect(Collectors.toList());
            }else {
                responseDTOList= Collections.emptyList();
            }
            log.debug("EmployeeService:getEmployee retrieving employee from database  {}",EmployeeMapper.jsonAsString(responseDTOList));
        }catch (Exception e){
            log.error("Exception occurred while retrieving employees from database , Exception message {}", e.getMessage());
            throw new EmployeeServiceException("Exception occurred while fetch all employees from Database");
        }

        log.info("EmployeeService:getEmployee execution ended.");
        return APIResponse.builder().status(true).message("OK").results(responseDTOList).build();

    }

    public APIResponse<?> getOneEmployee(Integer id){
        EmployeeResponseDTO employeeResponseDTO=null;
        try {
            log.info("EmployeeService : getEmployee execution started");
            Employee employee = employeeRepository.findById(id).orElseThrow(() -> new UserNotfoundException("Employee not found with id" + id));
            employeeResponseDTO = EmployeeMapper.toDTO(employee);
            log.debug("ProductService:getProductById retrieving employee from database for id {} {}", id, EmployeeMapper.jsonAsString(employeeResponseDTO));

        } catch (Exception ex) {
            log.error("Exception occurred while retrieving employee {} from database , Exception message {}", id, ex.getMessage());
            throw new EmployeeServiceException("Exception occurred while fetch employee from Database " + id);
        }
        log.info("EmployeeService:getEmployee execution ended.");
        return APIResponse.builder().status(true).message("OK").results(employeeResponseDTO).build();

    }

    public APIResponse<?> deleteEmployee(Integer id) {
        log.info("EmployeeService: delete employee execution started");
        try {
            employeeRepository.deleteById(id);
        }catch (Exception e){
            log.error("EmployeeService: Could not delete employee at given {}  {}",id,e.getMessage());
            throw new EmployeeServiceException (e.getMessage ());
        }
        log.info("EmployeeService: delete employee ");
        return APIResponse.builder().message("employee at given id deleted").status(true).build();
    }

    public Photo getOnePhoto(Integer id){
        Photo photo=null;
        try {
            log.info("EmployeeService : getOnePhoto execution started");
            photo = photoRepository.findById (id).orElseThrow (() -> new MultipartServiceException ("photo not found by id"));
        } catch (Exception ex) {
            log.error("Exception occurred while retrieving photo {} from database , Exception message {}", id, ex.getMessage());
            throw new EmployeeServiceException("Exception occurred while fetch photo from Database " + id);
        }
        log.info("EmployeeService:deletePhoto execution ended.");
        return photo;

    }
    public List<String> getMorePhoto(){
        List<String> image=null;
        try {
            log.info("EmployeeService : getOnePhoto execution started");
            image= photoRepository.findAll ().stream ().map (photo -> ImageUtils.convertImageToBase64 (photo.getBytes ())).toList ();
        } catch (Exception ex) {
            log.error("Exception occurred while retrieving photo from database , Exception message {}", ex.getMessage());
            throw new EmployeeServiceException("Exception occurred while fetch photo from Database ");
        }
        log.info("EmployeeService:deletePhoto execution ended.");

        return image;

    }
}
