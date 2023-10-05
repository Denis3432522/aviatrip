package com.example.aviatrip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

@SpringBootApplication
public class AviatripApplication {

    public static void main(String[] args) {
       SpringApplication.run(AviatripApplication.class, args);

//        LocalDateTime dateTime1 = LocalDateTime.now();
//
//        LocalDateTime dateTime2 = LocalDateTime.now(ZoneId.of("UTC")).plusDays(1);
//
//        Duration duration = Duration.between(dateTime1, dateTime2);
//
//        System.out.println(duration.getSeconds());
    }
}