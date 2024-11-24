package tn.iteam.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import tn.iteam.dtos.ErrorResponseDTO;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<ErrorResponseDTO> handleCompteAlreadyExistsException(UserAlreadyExists exception,
                                                                               WebRequest webRequest) {
        ErrorResponseDTO errorResponseDto = new ErrorResponseDTO(
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFound(ResourceNotFound exception,
                                                                               WebRequest webRequest) {
        ErrorResponseDTO errorResponseDto = new ErrorResponseDTO(
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

}
