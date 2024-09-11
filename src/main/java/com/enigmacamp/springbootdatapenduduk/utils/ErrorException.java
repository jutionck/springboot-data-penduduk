package com.enigmacamp.springbootdatapenduduk.utils;

import com.enigmacamp.springbootdatapenduduk.model.dto.response.ErrorResponseDto;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

public class ErrorException {
    public static ResponseEntity<ErrorResponseDto> handleResponseStatusException(ResponseStatusException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(
                ex.getStatusCode().value(),
                ex.getReason()
        );
        return new ResponseEntity<>(
                errorResponse,
                HttpStatusCode.valueOf(ex.getStatusCode().value())
        );
    }
}
