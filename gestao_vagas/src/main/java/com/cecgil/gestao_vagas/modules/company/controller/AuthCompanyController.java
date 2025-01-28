package com.cecgil.gestao_vagas.modules.company.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cecgil.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import com.cecgil.gestao_vagas.modules.company.services.AuthCompanyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/authCompany")
public class AuthCompanyController {

    @Autowired
    private AuthCompanyService authCompanyService;

    @PostMapping("/company")
    public ResponseEntity<Object> createAuthCompany(@RequestBody AuthCompanyDTO authCompanyDTO) {

        try {
            var result = this.authCompanyService.authCompany(authCompanyDTO);
            return ResponseEntity.ok().body(result);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        
        
        
    }
    
    
}
