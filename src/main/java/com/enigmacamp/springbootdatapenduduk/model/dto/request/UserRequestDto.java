package com.enigmacamp.springbootdatapenduduk.model.dto.request;

import com.enigmacamp.springbootdatapenduduk.model.enums.Role;
import lombok.Builder;
import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-data-penduduk
 * User: jutioncandrakirana
 * Email: jutionck@gmail.com
 * Telegram : jutionck
 * Date: 11/09/24
 * Time: 11.01
 * To change this template use File | Settings | File Templates.
 */

public class UserRequestDto {
    // inner class
    @Data
    @Builder
    public static class Register {
        private String username;
        private String password;
        private Role role;
    }

    @Data
    @Builder
    public static class Login {
        private String username;
        private String password;
    }
}
