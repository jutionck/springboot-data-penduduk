package com.enigmacamp.springbootdatapenduduk.model.dto.pagination;

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
