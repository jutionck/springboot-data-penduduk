package com.enigmacamp.springbootdatapenduduk.service;

import com.enigmacamp.springbootdatapenduduk.entity.model.Province;
import com.enigmacamp.springbootdatapenduduk.repository.ProvinceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ProvinceService implements BaseService<Province, Integer> {
    private final ProvinceRepository provinceRepository;

    @Override
    public Province create(Province payload) {
        provinceFieldValidate(payload);
        if (isCodeExist(payload.getCode())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Code already exists");
        }
        return provinceRepository.save(payload);
    }

    @Override
    public Page<Province> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(Math.max(page - 1, 0), size);
        return provinceRepository.findAll(pageable);
    }

    @Override
    public Province findById(Integer integer) {
        return provinceRepository.findById(integer)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Province not found"));
    }

    @Override
    public Province update(Province payload) {
        provinceFieldValidate(payload);
        Province existingProvince = findById(payload.getId());
        boolean codeExists = isCodeExist(payload.getCode());
        if (codeExists && !payload.getCode().equals(existingProvince.getCode())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Province code already exists");
        }
        existingProvince.setCode(payload.getCode());
        existingProvince.setName(payload.getName());

        return provinceRepository.save(existingProvince);
    }

    @Override
    public void delete(Integer id) {
        provinceRepository.delete(findById(id));
    }

    private boolean isCodeExist(String code) {
        return provinceRepository.existsByCode(code);
    }

    private static void provinceFieldValidate(Province payload) {
        if (payload.getCode() == null || payload.getCode().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Province code cannot be empty");
        }

        if (payload.getCode().length() > 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Province code cannot have more than 2 characters");
        }

        if (payload.getName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Province name cannot empty");
        }
    }
}
