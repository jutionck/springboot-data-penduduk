package com.enigmacamp.springbootdatapenduduk.controller;

import com.enigmacamp.springbootdatapenduduk.entity.dto.request.RegencyRequestDto;
import com.enigmacamp.springbootdatapenduduk.entity.dto.response.ErrorResponseDto;
import com.enigmacamp.springbootdatapenduduk.entity.dto.response.PaginationDto;
import com.enigmacamp.springbootdatapenduduk.entity.dto.response.ResponseEntityDto;
import com.enigmacamp.springbootdatapenduduk.entity.dto.response.ResponseStatusDto;
import com.enigmacamp.springbootdatapenduduk.entity.model.Regency;
import com.enigmacamp.springbootdatapenduduk.service.RegencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/regencies")
@RequiredArgsConstructor
public class RegencyController extends BaseController<Regency> {
    private final RegencyService regencyService;

    @PostMapping
    public ResponseEntity<?> createHandler(@RequestBody RegencyRequestDto payload) {
        try {
            Regency regency = regencyService.create(payload);
            ResponseEntityDto<Regency> response = this.sendResponse(
                    regency,
                    new ResponseStatusDto(
                            HttpStatus.CREATED.value(),
                            "Successfully created regency")
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
        Page<Regency> regencyPage = regencyService.findAll(page, size);
        PaginationDto paginationDto = new PaginationDto(
                page,
                size,
                (int) regencyPage.getTotalElements(),
                regencyPage.getTotalPages()
        );

        ResponseEntityDto<List<Regency>> response = this.sendPagedResponse(
                regencyPage,
                paginationDto,
                new ResponseStatusDto(
                        HttpStatus.OK.value(),
                        "Successfully fetched regencies")
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getHandler(@PathVariable int id) {
        try {
            Regency regency = regencyService.findById(id);
            ResponseEntityDto<Regency> response = this.sendResponse(
                    regency,
                    new ResponseStatusDto(
                            HttpStatus.OK.value(),
                            "Successfully get regency")
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
    public ResponseEntity<?> updateHandler(@RequestBody RegencyRequestDto payload) {
        try {
            Regency regency = regencyService.update(payload);
            ResponseEntityDto<Regency> response = this.sendResponse(
                    regency,
                    new ResponseStatusDto(
                            HttpStatus.OK.value(),
                            "Successfully updated regency")
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
            regencyService.delete(id);
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
