package com.enigmacamp.springbootdatapenduduk.controller;

import com.enigmacamp.springbootdatapenduduk.entity.dto.request.DistrictRequestDto;
import com.enigmacamp.springbootdatapenduduk.entity.dto.response.ErrorResponseDto;
import com.enigmacamp.springbootdatapenduduk.entity.dto.response.PaginationDto;
import com.enigmacamp.springbootdatapenduduk.entity.dto.response.ResponseEntityDto;
import com.enigmacamp.springbootdatapenduduk.entity.dto.response.ResponseStatusDto;
import com.enigmacamp.springbootdatapenduduk.entity.model.District;
import com.enigmacamp.springbootdatapenduduk.service.DistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/districts")
@RequiredArgsConstructor
public class DistrictController extends BaseController<District> {
    private final DistrictService districtService;

    @PostMapping
    public ResponseEntity<?> createHandler(@RequestBody DistrictRequestDto payload) {
        try {
            District district = districtService.create(payload);
            ResponseEntityDto<District> response = this.sendResponse(
                    district,
                    new ResponseStatusDto(
                            HttpStatus.CREATED.value(),
                            "Successfully created district")
            );
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (ResponseStatusException ex) {
            ErrorResponseDto errorResponse = this.sendErrorResponse(
                    ex.getStatusCode().value(),
                    ex.getReason()
            );
            return new ResponseEntity<>(
                    errorResponse,
                    HttpStatusCode.valueOf(ex.getStatusCode().value()));
        }
    }

    @GetMapping
    public ResponseEntity<?> listHandler(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<District> districtPage = districtService.findAll(page, size);
        PaginationDto paginationDto = new PaginationDto(
                page,
                size,
                (int) districtPage.getTotalElements(),
                districtPage.getTotalPages()
        );

        ResponseEntityDto<List<District>> response = this.sendPagedResponse(
                districtPage,
                paginationDto,
                new ResponseStatusDto(
                        HttpStatus.OK.value(),
                        "Successfully fetched districts")
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/city/{id}")
    public ResponseEntity<?> getByRegencyHandler(@PathVariable int id) {
        List<District> districts = districtService.findDistrictsByRegency(id);
        ResponseEntityDto<List<District>> response = this.sendPagedResponse(
                new PageImpl<>(districts),
                null,
                new ResponseStatusDto(
                        HttpStatus.OK.value(),
                        "Successfully fetched districts")
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getHandler(@PathVariable int id) {
        try {
            District district = districtService.findById(id);
            ResponseEntityDto<District> response = this.sendResponse(
                    district,
                    new ResponseStatusDto(
                            HttpStatus.OK.value(),
                            "Successfully get district")
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResponseStatusException ex) {
            ErrorResponseDto errorResponse = this.sendErrorResponse(
                    ex.getStatusCode().value(),
                    ex.getReason()
            );
            return new ResponseEntity<>(
                    errorResponse,
                    HttpStatusCode.valueOf(ex.getStatusCode().value()));
        }
    }


    @PutMapping
    public ResponseEntity<?> updateHandler(@RequestBody DistrictRequestDto payload) {
        try {
            District district = districtService.update(payload);
            ResponseEntityDto<District> response = this.sendResponse(
                    district,
                    new ResponseStatusDto(
                            HttpStatus.OK.value(),
                            "Successfully updated district")
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResponseStatusException ex) {
            ErrorResponseDto errorResponse = this.sendErrorResponse(
                    ex.getStatusCode().value(),
                    ex.getReason()
            );
            return new ResponseEntity<>(
                    errorResponse,
                    HttpStatusCode.valueOf(ex.getStatusCode().value()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHandler(@PathVariable int id) {
        try {
            districtService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (ResponseStatusException ex) {
            ErrorResponseDto errorResponse = this.sendErrorResponse(
                    ex.getStatusCode().value(),
                    ex.getReason()
            );
            return new ResponseEntity<>(
                    errorResponse,
                    HttpStatusCode.valueOf(ex.getStatusCode().value()));
        }
    }
}
