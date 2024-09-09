package com.enigmacamp.springbootdatapenduduk.repository;

import com.enigmacamp.springbootdatapenduduk.entity.model.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepository extends JpaRepository<District, Integer> {
    boolean existsByCode(String code);
    boolean existsByRegencyId(Integer regencyId);
    boolean existsByCodeAndRegencyId(String code, Integer regencyId);
}
