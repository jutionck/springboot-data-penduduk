package com.enigmacamp.springbootdatapenduduk.model.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SingleResponseDto<T> {
    private ResponseStatusDto status;
    private T data;
}

