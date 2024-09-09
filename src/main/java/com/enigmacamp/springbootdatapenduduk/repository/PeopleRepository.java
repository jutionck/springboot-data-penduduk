package com.enigmacamp.springbootdatapenduduk.repository;

import com.enigmacamp.springbootdatapenduduk.entity.model.People;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeopleRepository extends JpaRepository<People, Integer> {
    People findByNik(String nik);
    List<People> findByProvinceId(Integer provinceId);
    List<People> findByRegencyId(Integer regencyId);
    List<People> findByDistrictId(Integer districtId);
}
