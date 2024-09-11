package com.enigmacamp.springbootdatapenduduk.model.dto.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDto {
    private ResponseStatusDto status;
    public ErrorResponseDto(int code, String description) {
        this.status = new ResponseStatusDto(code, description);
    }
}