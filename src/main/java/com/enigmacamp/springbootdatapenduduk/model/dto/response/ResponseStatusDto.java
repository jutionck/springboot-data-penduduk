package com.enigmacamp.springbootdatapenduduk.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseStatusDto {
    private Integer code;
    private String description;
}

