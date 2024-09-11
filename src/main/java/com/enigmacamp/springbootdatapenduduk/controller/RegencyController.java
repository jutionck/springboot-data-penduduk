package com.enigmacamp.springbootdatapenduduk.controller;

import com.enigmacamp.springbootdatapenduduk.config.AppResponse;
import com.enigmacamp.springbootdatapenduduk.config.AppRoutes;
import com.enigmacamp.springbootdatapenduduk.model.dto.request.QueryParamDto;
import com.enigmacamp.springbootdatapenduduk.model.dto.request.RegencyRequestDto;
import com.enigmacamp.springbootdatapenduduk.model.dto.response.*;
import com.enigmacamp.springbootdatapenduduk.model.dto.pagination.PaginationDto;
import com.enigmacamp.springbootdatapenduduk.model.entity.Regency;
import com.enigmacamp.springbootdatapenduduk.service.RegencyService;
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
public class RegencyController extends BaseController<Regency> {
    private final RegencyService regencyService;

    @PostMapping(AppRoutes.POST_REGENCY)
    public ResponseEntity<?> createHandler(@RequestBody RegencyRequestDto payload) {
        try {
            Regency regency = regencyService.create(payload);
            SingleResponseDto<Regency> response = this.sendResponse(
                    regency,
                    new ResponseStatusDto(
                            HttpStatus.CREATED.value(),
                            AppResponse.SUCCESS_CREATED)
            );
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (ResponseStatusException ex) {
            return ErrorException.handleResponseStatusException(ex);
        }
    }

    @GetMapping(AppRoutes.GET_REGENCY_LIST)
    public ResponseEntity<?> listHandler(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String q
    ) {
        Page<Regency> regencyPage = regencyService.findAll(new QueryParamDto(q, page, size));
        PaginationDto paginationDto = new PaginationDto(
                page,
                size,
                (int) regencyPage.getTotalElements(),
                regencyPage.getTotalPages()
        );

        PageResponseDto<Regency> response = this.sendPagedResponse(
                regencyPage,
                paginationDto,
                new ResponseStatusDto(
                        HttpStatus.OK.value(),
                        AppResponse.SUCCESS_RETRIEVE_LIST)
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(AppRoutes.GET_REGENCY_BY_PROVINCE)
    public ResponseEntity<?> getByProvinceHandler(@PathVariable int id) {
        List<Regency> regencies = regencyService.findByProvince(id);
        PageResponseDto<Regency> response = this.sendPagedResponse(
                new PageImpl<>(regencies),
                null,
                new ResponseStatusDto(
                        HttpStatus.OK.value(),
                        AppResponse.SUCCESS_RETRIEVE_LIST)
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(AppRoutes.GET_REGENCY)
    public ResponseEntity<?> getHandler(@PathVariable int id) {
        try {
            Regency regency = regencyService.findById(id);
            SingleResponseDto<Regency> response = this.sendResponse(
                    regency,
                    new ResponseStatusDto(
                            HttpStatus.OK.value(),
                            AppResponse.SUCCESS_RETRIEVE)
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResponseStatusException ex) {
            return ErrorException.handleResponseStatusException(ex);
        }
    }

    @PutMapping(AppRoutes.PUT_REGENCY)
    public ResponseEntity<?> updateHandler(@RequestBody RegencyRequestDto payload) {
        try {
            Regency regency = regencyService.update(payload);
            SingleResponseDto<Regency> response = this.sendResponse(
                    regency,
                    new ResponseStatusDto(
                            HttpStatus.OK.value(),
                            AppResponse.SUCCESS_UPDATED)
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResponseStatusException ex) {
            return ErrorException.handleResponseStatusException(ex);
        }
    }

    @DeleteMapping(AppRoutes.DELETE_REGENCY)
    public ResponseEntity<?> deleteHandler(@PathVariable int id) {
        try {
            regencyService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (ResponseStatusException ex) {
            return ErrorException.handleResponseStatusException(ex);
        }
    }
}
