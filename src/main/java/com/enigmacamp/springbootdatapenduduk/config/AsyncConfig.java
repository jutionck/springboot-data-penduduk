package com.enigmacamp.springbootdatapenduduk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {
    @Bean(name = "emailExecutor")
    public Executor emailExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3); // menentukan ukuran dari thread yang selalu 5
        executor.setMaxPoolSize(10); // menentukan maksimal dari thread jika melebihi batas dari thread core
        executor.setQueueCapacity(50); // menentukan kapasitas antrian
        executor.setThreadNamePrefix("emailExecutor-"); // menentukan nama thread dengan pattern emailExecutor-1, emailExecutor-2, dst...
        executor.setRejectedExecutionHandler((r, e) -> {
            System.out.println("Email executor rejected " + r.toString() + " " + e);
        });
        executor.initialize();
        return executor;
    }
}
