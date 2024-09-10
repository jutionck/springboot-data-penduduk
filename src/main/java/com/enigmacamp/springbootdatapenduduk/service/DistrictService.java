package com.enigmacamp.springbootdatapenduduk.service;

import com.enigmacamp.springbootdatapenduduk.entity.dto.request.DistrictRequestDto;
import com.enigmacamp.springbootdatapenduduk.entity.dto.request.QueryParamDto;
import com.enigmacamp.springbootdatapenduduk.entity.model.District;
import com.enigmacamp.springbootdatapenduduk.entity.model.Regency;
import com.enigmacamp.springbootdatapenduduk.repository.DistrictRepository;
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
public class DistrictService implements BaseService<District, Integer> {
    private final DistrictRepository districtRepository;
    private final RegencyService regencyService;

    @Override
    public District create(District payload) {
        return districtRepository.save(payload);
    }

    public District create(DistrictRequestDto payload) {
        districtFieldValidate(payload);
        Regency regency = regencyService.findById(payload.getRegencyId());
        if (districtRepository.existsByRegencyId(
                payload.getRegencyId()) &&
                districtRepository.existsByCode(payload.getCode())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "District already exists");
        }
        District district = District.builder()
                .code(payload.getCode())
                .name(payload.getName())
                .regency(regency)
                .build();
        return districtRepository.save(district);
    }

    @Override
    public District update(District payload) {
        return districtRepository.save(payload);
    }

    public District update(DistrictRequestDto payload) {
        districtFieldValidate(payload);
        District existingDistrict = findById(payload.getId());
        boolean codeExists = districtRepository.existsByCodeAndRegencyId(payload.getCode(), payload.getRegencyId());
        if (codeExists && !(payload.getCode().equals(existingDistrict.getCode()) &&
                payload.getRegencyId().equals(existingDistrict.getRegency().getId()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "District code already exists for this regency");
        }
        existingDistrict.setCode(payload.getCode());
        existingDistrict.setName(payload.getName());
        existingDistrict.setRegency(regencyService.findById(payload.getRegencyId()));
        return districtRepository.save(existingDistrict);
    }

    @Override
    public Page<District> findAll(QueryParamDto params) {
        Pageable pageable = PageRequest.of(Math.max(params.getPage() - 1, 0), params.getSize());
        Specification<District> specification = DistrictRepository.hasSearchQuery(params);
        return districtRepository.findAll(specification, pageable);
    }

    public List<District> findDistrictsByRegency(Integer regencyId) {
        return districtRepository.findDistrictsByRegencyId(regencyId);
    }

    @Override
    public District findById(Integer integer) {
        return districtRepository.findById(integer)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "District not found"));
    }

    @Override
    public void delete(Integer id) {
        districtRepository.delete(findById(id));
    }

    private boolean isCodeExist(String code) {
        return districtRepository.existsByCode(code);
    }

    private static void districtFieldValidate(DistrictRequestDto payload) {
        if (payload.getCode() == null || payload.getCode().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "District code cannot be empty");
        }

        if (payload.getCode().length() > 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "District code cannot have more than 2 characters");
        }

        if (payload.getName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "District name cannot empty");
        }
    }
}
