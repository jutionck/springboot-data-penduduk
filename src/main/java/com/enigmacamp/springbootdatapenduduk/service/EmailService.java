package com.enigmacamp.springbootdatapenduduk.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Async("emailExecutor") // memanggil executor yang sudah dibuat pada file AsyncConfig
    public <T> void sendEmail(String to, T data) {
        try {
            // Simulasi waktu pengiriman email
            Thread.sleep(5000);
            // Cetak email dan data yang dikirim
            System.out.println("Sending email to " + to + " with data: " + data.toString());
        } catch (InterruptedException e) {
            // Mengembalikan status interrupt jika sleep diinterupsi
            Thread.currentThread().interrupt();
            System.out.println("Thread was interrupted: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred while sending email: " + e.getMessage());
        }
    }
}

