package com.enigmacamp.springbootdatapenduduk.entity.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SingleResponseDto<T> implements ResponseEntityDto<T> {
    private ResponseStatusDto status;
    private T data;
}

