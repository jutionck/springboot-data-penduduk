package com.enigmacamp.springbootdatapenduduk.controller;

import com.enigmacamp.springbootdatapenduduk.entity.dto.response.*;
import com.enigmacamp.springbootdatapenduduk.entity.model.Province;
import com.enigmacamp.springbootdatapenduduk.service.ProvinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@RequestMapping("/api/v1/provinces")
@RequiredArgsConstructor
public class ProvinceController extends BaseController<Province> {
    private final ProvinceService provinceService;

    @PostMapping
    public ResponseEntity<?> createHandler(@RequestBody Province payload) {
        try {
            Province province = provinceService.create(payload);
            ResponseEntityDto<Province> response = this.sendResponse(
                    province,
                    new ResponseStatusDto(
                            HttpStatus.CREATED.value(),
                            "Successfully created province")
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
        Page<Province> provincePage = provinceService.findAll(page, size);
        PaginationDto paginationDto = new PaginationDto(
                page,
                size,
                (int) provincePage.getTotalElements(),
                provincePage.getTotalPages()
        );

        ResponseEntityDto<List<Province>> response = this.sendPagedResponse(
                provincePage,
                paginationDto,
                new ResponseStatusDto(
                        HttpStatus.OK.value(),
                        "Successfully fetched provinces")
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getHandler(@PathVariable int id) {
        try {
            Province province = provinceService.findById(id);
            ResponseEntityDto<Province> response = this.sendResponse(
                    province,
                    new ResponseStatusDto(
                            HttpStatus.OK.value(),
                            "Successfully get province")
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
    public ResponseEntity<?> updateHandler(@RequestBody Province payload) {
        try {
            Province province = provinceService.update(payload);
            ResponseEntityDto<Province> response = this.sendResponse(
                    province,
                    new ResponseStatusDto(
                            HttpStatus.OK.value(),
                            "Successfully updated province")
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
            provinceService.delete(id);
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
