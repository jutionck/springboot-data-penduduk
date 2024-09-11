package com.enigmacamp.springbootdatapenduduk.controller;

import com.enigmacamp.springbootdatapenduduk.config.AppResponse;
import com.enigmacamp.springbootdatapenduduk.config.AppRoutes;
import com.enigmacamp.springbootdatapenduduk.model.dto.request.PeopleRequestDto;
import com.enigmacamp.springbootdatapenduduk.model.dto.request.QueryParamDto;
import com.enigmacamp.springbootdatapenduduk.model.dto.response.*;
import com.enigmacamp.springbootdatapenduduk.model.dto.pagination.PaginationDto;
import com.enigmacamp.springbootdatapenduduk.model.entity.People;
import com.enigmacamp.springbootdatapenduduk.service.PeopleService;
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
public class PeopleController extends BaseController<People> {
    private final PeopleService peopleService;

    @PostMapping(AppRoutes.POST_PEOPLE)
    public ResponseEntity<?> createHandler(@RequestBody PeopleRequestDto payload) {
        try {
            People person = peopleService.create(payload);
            SingleResponseDto<People> response = this.sendResponse(
                    person,
                    new ResponseStatusDto(
                            HttpStatus.CREATED.value(),
                            AppResponse.SUCCESS_CREATED)
            );
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (ResponseStatusException ex) {
            return ErrorException.handleResponseStatusException(ex);
        }
    }

    @GetMapping(AppRoutes.GET_PEOPLE_LIST)
    public ResponseEntity<?> listHandler(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String q
    ) {
        Page<People> people = peopleService.findAll(new QueryParamDto(q, page, size));
        PaginationDto paginationDto = new PaginationDto(
                page,
                size,
                (int) people.getTotalElements(),
                people.getTotalPages()
        );

        PageResponseDto<People> response = this.sendPagedResponse(
                people,
                paginationDto,
                new ResponseStatusDto(
                        HttpStatus.OK.value(),
                        AppResponse.SUCCESS_RETRIEVE_LIST)
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(AppRoutes.GET_PEOPLE_BY_PROVINCE)
    public ResponseEntity<?> getByProvinceHandler(@PathVariable int id) {
        List<People> people = peopleService.findByProvince(id);
        PageResponseDto<People> response = this.sendPagedResponse(
                new PageImpl<>(people),
                null,
                new ResponseStatusDto(
                        HttpStatus.OK.value(),
                        AppResponse.SUCCESS_RETRIEVE_LIST)
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(AppRoutes.GET_PEOPLE_BY_REGENCY)
    public ResponseEntity<?> getByRegencyHandler(@PathVariable int id) {
        List<People> people = peopleService.findByRegency(id);
        PageResponseDto<People> response = this.sendPagedResponse(
                new PageImpl<>(people),
                null,
                new ResponseStatusDto(
                        HttpStatus.OK.value(),
                        AppResponse.SUCCESS_RETRIEVE_LIST)
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(AppRoutes.GET_PEOPLE_BY_DISTRICT)
    public ResponseEntity<?> getByDistrictHandler(@PathVariable int id) {
        List<People> people = peopleService.findByDistrict(id);
        PageResponseDto<People> response = this.sendPagedResponse(
                new PageImpl<>(people),
                null,
                new ResponseStatusDto(
                        HttpStatus.OK.value(),
                        AppResponse.SUCCESS_RETRIEVE_LIST)
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(AppRoutes.GET_PEOPLE)
    public ResponseEntity<?> getHandler(@PathVariable int id) {
        try {
            People person = peopleService.findById(id);
            SingleResponseDto<People> response = this.sendResponse(
                    person,
                    new ResponseStatusDto(
                            HttpStatus.OK.value(),
                            AppResponse.SUCCESS_RETRIEVE)
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResponseStatusException ex) {
            return ErrorException.handleResponseStatusException(ex);
        }
    }
}
