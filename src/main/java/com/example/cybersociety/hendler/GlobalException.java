package com.example.cybersociety.hendler;

import com.example.cybersociety.exception.*;
import com.example.cybersociety.response.APIResponse;
import com.example.cybersociety.response.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public APIResponse<?> validArgument(MethodArgumentNotValidException e){
        List<ResponseError> error= new ArrayList<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            error.add(ResponseError.builder().status(fieldError.hashCode()).message(fieldError.getDefaultMessage()).build());
        });
        return APIResponse.builder().status(false).message("ERROR").results(error).build();
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    public APIResponse<?> forbidden(HttpClientErrorException.Forbidden forbidden){
        ResponseError error= ResponseError.builder().status(403).message(forbidden.getMessage()).build();
        return APIResponse.builder().status(false).message("ERROR").results(error).build();
    }

    @ExceptionHandler(ContactUsNotFoundException.class)
    public APIResponse<?> contactUsFoundException(ContactUsNotFoundException exception){
        ResponseError error= ResponseError.builder().status(204).message(exception.getMessage()).build();
        return APIResponse.builder().status(false).message("ERROR").results(error).build();
    }

    @ExceptionHandler(ContactUsServiceException.class)
    public APIResponse<?> serviceContactUsException(ContactUsServiceException e){
        ResponseError error= ResponseError.builder().status(null).message(e.getMessage()).build();
        return APIResponse.builder().status(false).message("ERROR").results(error).build();
    }

    @ExceptionHandler(EmployeeServiceException.class)
    public APIResponse<?> serviceEmployeeException(EmployeeServiceException e){
        ResponseError error=ResponseError.builder().status(204).message(e.getMessage()).build();
        return APIResponse.builder().status(false).message("ERROR").results(error).build();
    }

    @ExceptionHandler(PortfolioServiceException.class)
    public APIResponse<?> portfolioException(PortfolioServiceException e){
        ResponseError error=ResponseError.builder().status(204).message(e.getMessage()).build();
        return APIResponse.builder().status(false).message("ERROR").results(error).build();
    }

    @ExceptionHandler(ArticlesServiceException.class)
    public APIResponse<?> articlesException(ArticlesServiceException exception){
        ResponseError error= ResponseError.builder().status(204).message(exception.getMessage()).build();
        return APIResponse.builder().status(false).message("ERROR").results(error).build();
    }


    @ExceptionHandler(MultipartServiceException.class)
    public APIResponse<?> multipartException(MultipartServiceException e){
        ResponseError error=ResponseError.builder().status(204).message(e.getMessage()).build();
        return APIResponse.builder().status(false).message("ERROR").results(error).build();
    }

    @ExceptionHandler(UserNotfoundException.class)
    public APIResponse<?> employeeNotFoundException(UserNotfoundException exception){
        ResponseError error= ResponseError.builder().status(204).message(exception.getMessage()).build();
        return APIResponse.builder().status(false).message("ERROR").results(error).build();
    }




    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<?> handleSqlError(SQLException e) {
        Map<String, String> errors = new LinkedHashMap<>();
        if ("23503".equals(e.getSQLState())) {
            String message = e.getMessage();
            message = message.substring(0, message.length() - 2);
            String obj = message.substring(message.lastIndexOf("\"") + 1);
            String id = message.substring(message.lastIndexOf("(") + 1, message.lastIndexOf(")"));
            errors.put("message", obj + " with id=(" + id + ") not found");
        } else {
            errors.put("message", e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
        }
        return ResponseEntity.status(400).body(APIResponse.<Map<String, String>>builder()
                .message("Error data saving")
                .results(errors)
                .build());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> handleSizeException() {
        return ResponseEntity.badRequest().body(APIResponse.builder().status(false)
                .message("{file.getSize()}  File size must be less than " + 20)
                .build());
    }



    @ExceptionHandler(value = {AsyncRequestTimeoutException.class})
    public APIResponse<?> handleException(AsyncRequestTimeoutException ex) {
        ex.printStackTrace();
        ResponseError error= ResponseError.builder().message(ex.getMessage()).status(503).build();
        return APIResponse.builder().status(false).message("").results(error).build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public APIResponse<?> handleException(HttpMessageNotReadableException ex) {
        ex.printStackTrace();
        ResponseError error= ResponseError.builder().message(ex.getMessage()).status(503).build();
        return APIResponse.builder().status(false).message("").results(error).build();
    }


}

