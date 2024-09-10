package com.enigmacamp.springbootdatapenduduk.controller;

import com.enigmacamp.springbootdatapenduduk.config.AppResponse;
import com.enigmacamp.springbootdatapenduduk.config.AppRoutes;
import com.enigmacamp.springbootdatapenduduk.entity.dto.pagination.PaginationDto;
import com.enigmacamp.springbootdatapenduduk.entity.dto.response.*;
import com.enigmacamp.springbootdatapenduduk.entity.model.Province;
import com.enigmacamp.springbootdatapenduduk.service.ProvinceService;
import com.enigmacamp.springbootdatapenduduk.utils.ErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(AppRoutes.API_VERSION)
@RequiredArgsConstructor
public class ProvinceController extends BaseController<Province> {
    private final ProvinceService provinceService;

    @PostMapping(AppRoutes.POST_PROVINCE)
    public ResponseEntity<?> createHandler(@RequestBody Province payload) {
        try {
            Province province = provinceService.create(payload);
            SingleResponseDto<Province> response = this.sendResponse(
                    province,
                    new ResponseStatusDto(
                            HttpStatus.CREATED.value(),
                            AppResponse.SUCCESS_CREATED)
            );
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (ResponseStatusException ex) {
            return ErrorException.handleResponseStatusException(ex);
        }
    }

    @GetMapping(AppRoutes.GET_PROVINCE_LIST)
    public ResponseEntity<?> listHandler(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Province> provinces = provinceService.findAll(page, size);
        PaginationDto paginationDto = new PaginationDto(
                page,
                size,
                (int) provinces.getTotalElements(),
                provinces.getTotalPages()
        );

        PageResponseDto<Province> response = this.sendPagedResponse(
                provinces,
                paginationDto,
                new ResponseStatusDto(
                        HttpStatus.OK.value(),
                        AppResponse.SUCCESS_RETRIEVE_LIST)
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(AppRoutes.GET_PROVINCE)
    public ResponseEntity<?> getHandler(@PathVariable int id) {
        try {
            Province province = provinceService.findById(id);
            SingleResponseDto<Province> response = this.sendResponse(
                    province,
                    new ResponseStatusDto(
                            HttpStatus.OK.value(),
                            AppResponse.SUCCESS_RETRIEVE)
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResponseStatusException ex) {
            return ErrorException.handleResponseStatusException(ex);
        }
    }

    @PutMapping(AppRoutes.PUT_PROVINCE)
    public ResponseEntity<?> updateHandler(@RequestBody Province payload) {
        try {
            Province province = provinceService.update(payload);
            SingleResponseDto<Province> response = this.sendResponse(
                    province,
                    new ResponseStatusDto(
                            HttpStatus.OK.value(),
                            AppResponse.SUCCESS_UPDATED)
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResponseStatusException ex) {
            return ErrorException.handleResponseStatusException(ex);
        }
    }

    @DeleteMapping(AppRoutes.DELETE_PROVINCE)
    public ResponseEntity<?> deleteHandler(@PathVariable int id) {
        try {
            provinceService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (ResponseStatusException ex) {
            return ErrorException.handleResponseStatusException(ex);
        }
    }

}
