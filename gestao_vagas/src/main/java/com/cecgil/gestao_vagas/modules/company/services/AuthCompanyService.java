package com.cecgil.gestao_vagas.modules.company.services;

import java.time.Duration;
import java.time.Instant;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cecgil.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import com.cecgil.gestao_vagas.modules.company.repository.CompanyRespository;

@Service
public class AuthCompanyService {

    //buscar valor do token "@Value" injeta o valor.
    @Value("${security.token.secret}")
    private String secretKey;
    
    @Autowired
    private CompanyRespository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String authCompany(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
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

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var token = JWT.create().withIssuer("vagas")
                    .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
                    .withSubject(company.getId().toString())
                    .sign(algorithm);

        return token;
    }
}
