package com.enigmacamp.springbootdatapenduduk.repository;

import com.enigmacamp.springbootdatapenduduk.entity.dto.request.QueryParamDto;
import com.enigmacamp.springbootdatapenduduk.entity.model.Province;
import com.enigmacamp.springbootdatapenduduk.specification.BaseSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Integer>, JpaSpecificationExecutor<Province> {
    boolean existsByCode(String code);
    static Specification<Province> hasSearchQuery(QueryParamDto params) {
        return BaseSpecification.hasSearchQuery(
                params.getQ(),
                new String[]{"code", "name"},
                null
        );
    }
}
