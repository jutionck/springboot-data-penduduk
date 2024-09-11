package com.enigmacamp.springbootdatapenduduk.model.dto.response;

import com.enigmacamp.springbootdatapenduduk.model.dto.pagination.PaginationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponseDto<T> {
    private ResponseStatusDto status;
    private List<T> data;
    private PaginationDto paging;
}
