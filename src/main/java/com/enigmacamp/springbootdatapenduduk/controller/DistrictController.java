package com.enigmacamp.springbootdatapenduduk.controller;

import com.enigmacamp.springbootdatapenduduk.config.AppResponse;
import com.enigmacamp.springbootdatapenduduk.config.AppRoutes;
import com.enigmacamp.springbootdatapenduduk.entity.dto.request.DistrictRequestDto;
import com.enigmacamp.springbootdatapenduduk.entity.dto.request.QueryParamDto;
import com.enigmacamp.springbootdatapenduduk.entity.dto.response.*;
import com.enigmacamp.springbootdatapenduduk.entity.dto.pagination.PaginationDto;
import com.enigmacamp.springbootdatapenduduk.entity.model.District;
import com.enigmacamp.springbootdatapenduduk.service.DistrictService;
import com.enigmacamp.springbootdatapenduduk.utils.ErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(AppRoutes.API_VERSION)
@RequiredArgsConstructor
public class DistrictController extends BaseController<District> {
    private final DistrictService districtService;

    @PostMapping(AppRoutes.POST_DISTRICT)
    public ResponseEntity<?> createHandler(@RequestBody DistrictRequestDto payload) {
        try {
            District district = districtService.create(payload);
            SingleResponseDto<District> response = this.sendResponse(
                    district,
                    new ResponseStatusDto(
                            HttpStatus.CREATED.value(),
                            AppResponse.SUCCESS_CREATED)
            );
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (ResponseStatusException ex) {
            return ErrorException.handleResponseStatusException(ex);
        }
    }

    @GetMapping(AppRoutes.GET_DISTRICT_LIST)
    public ResponseEntity<?> listHandler(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String q
    ) {
        Page<District> districtPage = districtService.findAll(new QueryParamDto(q, page, size));
        PaginationDto paginationDto = new PaginationDto(
                page,
                size,
                (int) districtPage.getTotalElements(),
                districtPage.getTotalPages()
        );

        PageResponseDto<District> response = this.sendPagedResponse(
                districtPage,
                paginationDto,
                new ResponseStatusDto(
                        HttpStatus.OK.value(),
                        AppResponse.SUCCESS_RETRIEVE_LIST)
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(AppRoutes.GET_DISTRICT_BY_REGENCY)
    public ResponseEntity<?> getByRegencyHandler(@PathVariable int id) {
        List<District> districts = districtService.findDistrictsByRegency(id);
        PageResponseDto<District> response = this.sendPagedResponse(
                new PageImpl<>(districts),
                null,
                new ResponseStatusDto(
                        HttpStatus.OK.value(),
                        AppResponse.SUCCESS_RETRIEVE_LIST)
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(AppRoutes.GET_DISTRICT)
    public ResponseEntity<?> getHandler(@PathVariable int id) {
        try {
            District district = districtService.findById(id);
            SingleResponseDto<District> response = this.sendResponse(
                    district,
                    new ResponseStatusDto(
                            HttpStatus.OK.value(),
                            AppResponse.SUCCESS_RETRIEVE)
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResponseStatusException ex) {
            return ErrorException.handleResponseStatusException(ex);
        }
    }

    @PutMapping(AppRoutes.PUT_DISTRICT)
    public ResponseEntity<?> updateHandler(@RequestBody DistrictRequestDto payload) {
        try {
            District district = districtService.update(payload);
            SingleResponseDto<District> response = this.sendResponse(
                    district,
                    new ResponseStatusDto(
                            HttpStatus.OK.value(),
                            AppResponse.SUCCESS_UPDATED)
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResponseStatusException ex) {
            return ErrorException.handleResponseStatusException(ex);
        }
    }

    @DeleteMapping(AppRoutes.DELETE_DISTRICT)
    public ResponseEntity<?> deleteHandler(@PathVariable int id) {
        try {
            districtService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (ResponseStatusException ex) {
            return ErrorException.handleResponseStatusException(ex);
        }
    }
}
