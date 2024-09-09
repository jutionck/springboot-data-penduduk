package com.enigmacamp.springbootdatapenduduk.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationDto {
    private Integer page;
    private Integer rowsPerPage;
    private Integer totalRows;
    private Integer totalPages;
}
