package com.example.aviatrip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

@SpringBootApplication
public class AviatripApplication {

    public static void main(String[] args) {
       SpringApplication.run(AviatripApplication.class, args);
    }
}