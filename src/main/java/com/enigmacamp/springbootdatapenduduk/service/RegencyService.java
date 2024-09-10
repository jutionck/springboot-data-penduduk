package com.enigmacamp.springbootdatapenduduk.service;

import com.enigmacamp.springbootdatapenduduk.entity.dto.request.QueryParamDto;
import com.enigmacamp.springbootdatapenduduk.entity.dto.request.RegencyRequestDto;
import com.enigmacamp.springbootdatapenduduk.entity.model.Province;
import com.enigmacamp.springbootdatapenduduk.entity.model.Regency;
import com.enigmacamp.springbootdatapenduduk.repository.RegencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegencyService implements BaseService<Regency, Integer> {
    private final RegencyRepository regencyRepository;
    private final ProvinceService provinceService;

    @Override
    public Regency create(Regency payload) {
        return regencyRepository.save(payload);
    }

    public Regency create(RegencyRequestDto payload) {
        regencyFieldValidate(payload);
        Province province = provinceService.findById(payload.getProvinceId());
        if (regencyRepository.existsByProvinceId(
                payload.getProvinceId()) &&
                regencyRepository.existsByCode(payload.getCode())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Regency already exists");
        }
        Regency regency = Regency.builder()
                .code(payload.getCode())
                .name(payload.getName())
                .province(province)
                .build();
        return regencyRepository.save(regency);
    }

    @Override
    public Regency update(Regency payload) {
        return regencyRepository.saveAndFlush(payload);
    }

    public Regency update(RegencyRequestDto payload) {
        regencyFieldValidate(payload);
        Regency existingRegency = findById(payload.getId());
        boolean codeExists = regencyRepository.existsByCodeAndProvinceId(payload.getCode(), payload.getProvinceId());
        if (codeExists && !(payload.getCode().equals(existingRegency.getCode()) &&
                payload.getProvinceId().equals(existingRegency.getProvince().getId()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Regency code already exists for this province");
        }
        existingRegency.setCode(payload.getCode());
        existingRegency.setName(payload.getName());
        existingRegency.setProvince(provinceService.findById(payload.getProvinceId()));
        return regencyRepository.save(existingRegency);
    }

    @Override
    public Page<Regency> findAll(QueryParamDto params) {
        Pageable pageable = PageRequest.of(Math.max(params.getPage() - 1, 0), params.getSize());
        Specification<Regency> specification = RegencyRepository.hasSearchQuery(params);
        return regencyRepository.findAll(specification, pageable);
    }

    @Override
    public Regency findById(Integer integer) {
        return regencyRepository.findById(integer).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Regency not found"));
    }

    public List<Regency> findByProvince(Integer provinceId) {
        return regencyRepository.findByProvinceId(provinceId);
    }

    @Override
    public void delete(Integer id) {
        regencyRepository.delete(findById(id));
    }

    private static void regencyFieldValidate(RegencyRequestDto payload) {
        if (payload.getCode().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Regency code cannot be empty");
        }

        if (payload.getCode().length() > 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Regency code cannot have more than 2 characters");
        }

        if (payload.getName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Regency name cannot empty");
        }
    }
}
