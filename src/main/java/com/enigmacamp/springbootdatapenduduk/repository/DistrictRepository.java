package com.enigmacamp.springbootdatapenduduk.repository;

import com.enigmacamp.springbootdatapenduduk.entity.dto.request.QueryParamDto;
import com.enigmacamp.springbootdatapenduduk.entity.model.District;
import com.enigmacamp.springbootdatapenduduk.specification.BaseSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DistrictRepository extends JpaRepository<District, Integer>, JpaSpecificationExecutor<District> {
    boolean existsByCode(String code);
    boolean existsByRegencyId(Integer regencyId);
    boolean existsByCodeAndRegencyId(String code, Integer regencyId);
    List<District> findDistrictsByRegencyId(Integer regencyId);
    static Specification<District> hasSearchQuery(QueryParamDto params) {
        return BaseSpecification.hasSearchQuery(
                params.getQ(),
                new String[]{"code", "name"},
                Map.of(
                        // Join dengan regency dan cari di field "name"
                        "regency", Map.of("name", "name")
                )
        );
    }
}
