package com.enigmacamp.springbootdatapenduduk.controller;

import com.enigmacamp.springbootdatapenduduk.entity.dto.request.PeopleRequestDto;
import com.enigmacamp.springbootdatapenduduk.entity.dto.response.ErrorResponseDto;
import com.enigmacamp.springbootdatapenduduk.entity.dto.response.PaginationDto;
import com.enigmacamp.springbootdatapenduduk.entity.dto.response.ResponseEntityDto;
import com.enigmacamp.springbootdatapenduduk.entity.dto.response.ResponseStatusDto;
import com.enigmacamp.springbootdatapenduduk.entity.model.People;
import com.enigmacamp.springbootdatapenduduk.service.PeopleService;
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
@RequestMapping("/api/v1/people")
@RequiredArgsConstructor
public class PeopleController extends BaseController<People> {
    private final PeopleService peopleService;

    @PostMapping
    public ResponseEntity<?> createHandler(@RequestBody PeopleRequestDto payload) {
        try {
            People person = peopleService.create(payload);
            ResponseEntityDto<People> response = this.sendResponse(
                    person,
                    new ResponseStatusDto(
                            HttpStatus.CREATED.value(),
                            "Successfully created person")
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
        Page<People> regencyPage = peopleService.findAll(page, size);
        PaginationDto paginationDto = new PaginationDto(
                page,
                size,
                (int) regencyPage.getTotalElements(),
                regencyPage.getTotalPages()
        );

        ResponseEntityDto<List<People>> response = this.sendPagedResponse(
                regencyPage,
                paginationDto,
                new ResponseStatusDto(
                        HttpStatus.OK.value(),
                        "Successfully fetched people")
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/province/{id}")
    public ResponseEntity<?> getByProvinceHandler(@PathVariable int id) {
        List<People> people = peopleService.findByProvince(id);
        ResponseEntityDto<List<People>> response = this.sendPagedResponse(
                new PageImpl<>(people),
                null,
                new ResponseStatusDto(
                        HttpStatus.OK.value(),
                        "Successfully fetched people by province")
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/city/{id}")
    public ResponseEntity<?> getByRegencyHandler(@PathVariable int id) {
        List<People> people = peopleService.findByRegency(id);
        ResponseEntityDto<List<People>> response = this.sendPagedResponse(
                new PageImpl<>(people),
                null,
                new ResponseStatusDto(
                        HttpStatus.OK.value(),
                        "Successfully fetched people by regency")
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/district/{id}")
    public ResponseEntity<?> getByDistrictHandler(@PathVariable int id) {
        List<People> people = peopleService.findByDistrict(id);
        ResponseEntityDto<List<People>> response = this.sendPagedResponse(
                new PageImpl<>(people),
                null,
                new ResponseStatusDto(
                        HttpStatus.OK.value(),
                        "Successfully fetched people by district")
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getHandler(@PathVariable int id) {
        try {
            People person = peopleService.findById(id);
            ResponseEntityDto<People> response = this.sendResponse(
                    person,
                    new ResponseStatusDto(
                            HttpStatus.OK.value(),
                            "Successfully get person")
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
}
