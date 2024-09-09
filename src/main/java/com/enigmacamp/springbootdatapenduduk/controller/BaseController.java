package com.enigmacamp.springbootdatapenduduk.controller;

import com.enigmacamp.springbootdatapenduduk.entity.dto.response.*;
import org.springframework.data.domain.Page;

import java.util.List;

public class BaseController<T> {
    protected ResponseEntityDto<T> sendResponse(T data, ResponseStatusDto status) {
        SingleResponseDto<T> response = new SingleResponseDto<>();
        response.setData(data);
        if (status != null) {
            response.setStatus(status);
        }
        return response;
    }

    protected ResponseEntityDto<List<T>> sendPagedResponse(Page<T> data, PaginationDto paging, ResponseStatusDto status) {
        PageResponseDto<T> response = new PageResponseDto<>();
        response.setData(data.getContent());
        response.setPaging(paging);
        if (status != null) {
            response.setStatus(status);
        }
        return response;
    }

    protected ErrorResponseDto sendErrorResponse(int code, String description) {
        return new ErrorResponseDto(code, description);
    }
}
