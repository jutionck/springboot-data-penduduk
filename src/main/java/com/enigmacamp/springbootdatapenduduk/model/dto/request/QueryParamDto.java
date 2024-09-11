package com.enigmacamp.springbootdatapenduduk.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryParamDto {
    private String q;
    private Integer page;
    private Integer size;
}
