package com.enigmacamp.springbootdatapenduduk.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponseDto<T> implements ResponseEntityDto<List<T>> {
    private ResponseStatusDto status;
    private List<T> data;
    private PaginationDto paging;
}
