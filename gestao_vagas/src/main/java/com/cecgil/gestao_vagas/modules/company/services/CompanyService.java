package com.cecgil.gestao_vagas.modules.company.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cecgil.gestao_vagas.exceptions.UserFoundException;
import com.cecgil.gestao_vagas.modules.company.entities.CompanyEntity;
import com.cecgil.gestao_vagas.modules.company.repository.CompanyRespository;

@Service
public class CompanyService {

    @Autowired
    private CompanyRespository companyRepository;

    public CompanyEntity createCompany(CompanyEntity companyEntity) {

        this.companyRepository
        .findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getEmail())
        .ifPresent((company) -> {
            throw new UserFoundException();
        });

        return this.companyRepository.save(companyEntity);
    }
    
}
