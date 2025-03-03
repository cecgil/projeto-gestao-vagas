package com.cecgil.gestao_vagas.modules.company.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cecgil.gestao_vagas.exceptions.UserFoundException;
import com.cecgil.gestao_vagas.modules.company.entities.CompanyEntity;
import com.cecgil.gestao_vagas.modules.company.repository.CompanyRespository;

@Service
public class CompanyService {

    @Autowired
    private CompanyRespository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CompanyEntity createCompany(CompanyEntity companyEntity) {

        this.companyRepository
        .findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getEmail())
        .ifPresent((company) -> {
            throw new UserFoundException();
        });

        var password = passwordEncoder.encode(companyEntity.getPassword());
        companyEntity.setPassword(password);

        return this.companyRepository.save(companyEntity);
    }
    
}
