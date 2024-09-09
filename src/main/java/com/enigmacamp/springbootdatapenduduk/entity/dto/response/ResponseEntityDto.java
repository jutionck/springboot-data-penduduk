package com.enigmacamp.springbootdatapenduduk.entity.dto.response;

public interface ResponseEntityDto<T> {
    ResponseStatusDto getStatus();
    T getData();
}
