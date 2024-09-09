package com.enigmacamp.springbootdatapenduduk.service;

import org.springframework.data.domain.Page;

public interface BaseService<T, ID> {
    T create(T payload);
    T update(T payload);
    Page<T> findAll(int page, int size);
    T findById(ID id);
    void delete(ID id);
}
