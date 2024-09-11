package com.enigmacamp.springbootdatapenduduk.controller;

import com.enigmacamp.springbootdatapenduduk.config.AppResponse;
import com.enigmacamp.springbootdatapenduduk.config.AppRoutes;
import com.enigmacamp.springbootdatapenduduk.model.dto.request.DistrictRequestDto;
import com.enigmacamp.springbootdatapenduduk.model.dto.request.UserRequestDto;
import com.enigmacamp.springbootdatapenduduk.model.dto.response.ResponseStatusDto;
import com.enigmacamp.springbootdatapenduduk.model.dto.response.SingleResponseDto;
import com.enigmacamp.springbootdatapenduduk.model.entity.District;
import com.enigmacamp.springbootdatapenduduk.model.entity.User;
import com.enigmacamp.springbootdatapenduduk.service.UserService;
import com.enigmacamp.springbootdatapenduduk.utils.ErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(AppRoutes.API_VERSION)
@RequiredArgsConstructor
public class AuthController extends BaseController<User> {
    private final UserService userService;

    @PostMapping(AppRoutes.LOGIN)
    public ResponseEntity<?> loginHandler(@RequestBody UserRequestDto.Login payload) {
        try {
            String token = userService.login(payload);
            Map<String, Object> responseBody = new HashMap<>();
            Map<String, Object> status = new HashMap<>();
            status.put("code", HttpStatus.OK.value());
            status.put("description", HttpStatus.OK.getReasonPhrase());
            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            responseBody.put("status", status);
            responseBody.put("data", data);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(responseBody);
        } catch (ResponseStatusException ex) {
            return ErrorException.handleResponseStatusException(ex);
        }
    }

    @PostMapping(AppRoutes.REGISTER)
    public ResponseEntity<?> createHandler(@RequestBody UserRequestDto.Register payload) {
        try {
            User user = userService.registerNewUser(payload);
            SingleResponseDto<User> response = this.sendResponse(
                    user,
                    new ResponseStatusDto(
                            HttpStatus.CREATED.value(),
                            AppResponse.SUCCESS_CREATED)
            );
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (ResponseStatusException ex) {
            return ErrorException.handleResponseStatusException(ex);
        }
    }
}
