package com.enigmacamp.springbootdatapenduduk.repository;

import com.enigmacamp.springbootdatapenduduk.model.dto.request.QueryParamDto;
import com.enigmacamp.springbootdatapenduduk.model.entity.People;
import com.enigmacamp.springbootdatapenduduk.specification.BaseSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PeopleRepository extends JpaRepository<People, Integer>, JpaSpecificationExecutor<People> {
    People findByNik(String nik);
    List<People> findByProvinceId(Integer provinceId);
    List<People> findByRegencyId(Integer regencyId);
    List<People> findByDistrictId(Integer districtId);
    static Specification<People> hasSearchQuery(QueryParamDto params) {
        return BaseSpecification.hasSearchQuery(
                params.getQ(),
                new String[]{"name", "nik", "placeOfBirth", "gender"},  // Field dari People
                Map.of(
                        "province", Map.of("name", "name"),   // Join dengan Province
                        "regency", Map.of("name", "name"),    // Join dengan Regency
                        "district", Map.of("name", "name")    // Join dengan District
                )
        );
    }
}
