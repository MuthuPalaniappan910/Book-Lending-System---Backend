package com.booklending.customer.exception; 
 
import com.booklending.customer.dto.CommonResponse; 
import org.springframework.http.HttpHeaders; 
import org.springframework.http.HttpStatus; 
import org.springframework.http.HttpStatusCode; 
import org.springframework.http.ResponseEntity; 
import org.springframework.validation.FieldError; 
import org.springframework.web.bind.MethodArgumentNotValidException; 
import org.springframework.web.bind.annotation.ExceptionHandler; 
import org.springframework.web.bind.annotation.RestControllerAdvice; 
import org.springframework.web.context.request.WebRequest; 
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler; 
 
import java.util.Date; 
import java.util.LinkedHashMap; 
import java.util.List; 
import java.util.Map; 
import java.util.stream.Collectors; 
 
@RestControllerAdvice 
public class CustomExceptionHandler extends ResponseEntityExceptionHandler { 
 
    @Override 
    protected ResponseEntity<Object> handleMethodArgumentNotValid( 
            MethodArgumentNotValidException ex, 
            HttpHeaders headers, HttpStatusCode status, WebRequest request) { 
        Map<String, Object> errorResponse = new LinkedHashMap<>(); 
        errorResponse.put("timestamp", new Date()); 
        errorResponse.put("result",Boolean.FALSE); 
        List<String> errors = ex.getBindingResult().getFieldErrors().stream() 
                .map(FieldError::getDefaultMessage).collect(Collectors.toList()); 
        errorResponse.put("errors", errors); 
        return new ResponseEntity<>(errorResponse, headers, HttpStatus.OK); 
    } 
 
    @ExceptionHandler(JwtException.class) 
    private ResponseEntity<CommonResponse> handleJwtException(JwtException exception) { 
        CommonResponse commonResponse = new CommonResponse(); 
        commonResponse.setResult(Boolean.FALSE); 
        commonResponse.setMessage(exception.getMessage()); 
        return new ResponseEntity<>(commonResponse, HttpStatus.UNAUTHORIZED); 
    } 
} 
