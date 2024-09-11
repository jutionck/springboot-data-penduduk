package com.enigmacamp.springbootdatapenduduk.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeopleRequestDto {
    private Integer id;
    private String name;
    private String gender;
    @JsonProperty("place_of_birth")
    private String placeOfBirth;
    private Date bod;
    @JsonProperty("province_id")
    private Integer provinceId;
    @JsonProperty("regency_id")
    private Integer regencyId;
    @JsonProperty("district_id")
    private Integer districtId;
}
