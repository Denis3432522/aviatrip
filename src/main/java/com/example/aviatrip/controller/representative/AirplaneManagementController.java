package com.example.aviatrip.controller.representative;

import com.example.aviatrip.model.entity.Airplane;
import com.example.aviatrip.service.AirplaneService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/representative/airplanes")
public class AirplaneManagementController {

    private final AirplaneService airplaneService;

    public AirplaneManagementController(AirplaneService airplaneService) {
        this.airplaneService = airplaneService;
    }

    @PostMapping
    public void createAirplane(@RequestBody @Valid Airplane airplane, @AuthenticationPrincipal Long userId) {
        airplaneService.createAirplane(airplane, userId);
    }
}
