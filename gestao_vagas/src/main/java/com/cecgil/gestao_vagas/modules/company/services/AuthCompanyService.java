package com.cecgil.gestao_vagas.modules.company.services;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cecgil.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import com.cecgil.gestao_vagas.modules.company.repository.CompanyRespository;

@Service
public class AuthCompanyService {
    
    @Autowired
    private CompanyRespository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void authCompany(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
        var company = this.companyRepository
            .findByUsername(authCompanyDTO.getUsername())
            .orElseThrow(
                () -> {
                    throw new UsernameNotFoundException("Company not found");
                });
                
        var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());
        
        if(!passwordMatches) {
            throw new AuthenticationException();
        }
    
    }
}
