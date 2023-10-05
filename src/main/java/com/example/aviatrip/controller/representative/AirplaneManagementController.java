package com.example.aviatrip.controller.representative;

import com.example.aviatrip.model.entity.Airplane;
import com.example.aviatrip.service.AirplaneService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public List<Airplane> getCompanyAirplanes(@RequestParam(value = "page", defaultValue = "0") int pageNumber, @AuthenticationPrincipal Long companyId) {
        Pageable pageRequest = PageRequest.of(pageNumber, 5);
        return airplaneService.getCompanyAirplanes(companyId, pageRequest);
    }

    @GetMapping("/{filter}")
    public Airplane getCompanyAirplane(@PathVariable String filter, @AuthenticationPrincipal Long companyId) {

        try {
            return airplaneService.getCompanyAirplaneById(Integer.parseInt(filter), companyId);
        } catch (NumberFormatException ex) {
            return airplaneService.getCompanyAirplaneByModel(filter, companyId);
        }
    }
}
