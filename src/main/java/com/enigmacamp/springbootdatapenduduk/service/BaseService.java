package com.enigmacamp.springbootdatapenduduk.service;

import com.enigmacamp.springbootdatapenduduk.model.dto.request.QueryParamDto;
import org.springframework.data.domain.Page;

public interface BaseService<T, ID> {
    T create(T payload);
    T update(T payload);
    Page<T> findAll(QueryParamDto params);
    T findById(ID id);
    void delete(ID id);
}
