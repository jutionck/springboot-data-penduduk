package com.enigmacamp.springbootdatapenduduk.repository;

import com.enigmacamp.springbootdatapenduduk.model.dto.request.QueryParamDto;
import com.enigmacamp.springbootdatapenduduk.model.entity.Regency;
import com.enigmacamp.springbootdatapenduduk.specification.BaseSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RegencyRepository extends JpaRepository<Regency, Integer>, JpaSpecificationExecutor<Regency> {
    boolean existsByCode(String code);
    boolean existsByProvinceId(Integer provinceId);
    boolean existsByCodeAndProvinceId(String code, Integer provinceId);
    List<Regency> findByProvinceId(Integer provinceId);
    static Specification<Regency> hasSearchQuery(QueryParamDto params) {
        return BaseSpecification.hasSearchQuery(
                params.getQ(),
                new String[]{"code", "name"},
                Map.of(
                        // Join dengan Province dan cari di field "name"
                        "province", Map.of("name", "name")
                )
        );
    }
}
