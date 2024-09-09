package com.enigmacamp.springbootdatapenduduk.repository;

import com.enigmacamp.springbootdatapenduduk.entity.model.Regency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegencyRepository extends JpaRepository<Regency, Integer> {
    boolean existsByCode(String code);
    boolean existsByProvinceId(Integer provinceId);
    boolean existsByCodeAndProvinceId(String code, Integer provinceId);
}
