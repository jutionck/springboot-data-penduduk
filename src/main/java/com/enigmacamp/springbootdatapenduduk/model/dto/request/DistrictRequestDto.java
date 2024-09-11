package com.enigmacamp.springbootdatapenduduk.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistrictRequestDto {
    private Integer id;
    private String code;
    private String name;
    @JsonProperty("regency_id")
    private Integer regencyId;
}
