package com.example.aviatrip.service;

import com.example.aviatrip.config.exception.ValueNotUniqueException;
import com.example.aviatrip.config.requestmodel.RepresentativeModel;
import com.example.aviatrip.model.AviaCompany;
import com.example.aviatrip.model.AviaCompanyRepresentative;
import com.example.aviatrip.model.User;
import com.example.aviatrip.repository.CompanyRepository;
import com.example.aviatrip.repository.RepresentativeRepository;
import org.springframework.stereotype.Service;

@Service
public class RepresentativeService {

    private final RepresentativeRepository representativeRepository;
    private final CompanyRepository companyRepository;

    public RepresentativeService(RepresentativeRepository representativeRepository, CompanyRepository companyRepository) {
        this.representativeRepository = representativeRepository;
        this.companyRepository = companyRepository;
    }

    public void assertCompanyNameUniqueness(String companyName) {
        if(companyRepository.existsByName(companyName))
            throw new ValueNotUniqueException("company name", true);
    }

    public void createRepresentativeAndCompany(User model, String companyName) {
        AviaCompanyRepresentative representative = representativeRepository.save(new AviaCompanyRepresentative(model));
        AviaCompany aviaCompany = new AviaCompany(companyName, representative);
        companyRepository.save(aviaCompany);
    }
}
