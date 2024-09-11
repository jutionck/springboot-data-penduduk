package com.enigmacamp.springbootdatapenduduk.repository;

import com.enigmacamp.springbootdatapenduduk.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-data-penduduk
 * User: jutioncandrakirana
 * Email: jutionck@gmail.com
 * Telegram : jutionck
 * Date: 11/09/24
 * Time: 10.58
 * To change this template use File | Settings | File Templates.
 */

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
