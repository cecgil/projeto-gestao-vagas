package com.cecgil.gestao_vagas.modules.candidate.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cecgil.gestao_vagas.modules.candidate.entity.CandidateEntity;
import com.cecgil.gestao_vagas.modules.candidate.services.CandidateService;
import com.cecgil.gestao_vagas.modules.candidate.services.ProfileCandidateService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/candidate")
public class CandidateController {
    
    @Autowired
    CandidateService candidateService;

    @Autowired
    ProfileCandidateService profileCandidateService;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity) {
        try{ 
            var result =  this.candidateService.creteCandidate(candidateEntity);
            return ResponseEntity.ok().body(result);
        } catch(Exception e ) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        
    }

    @GetMapping("/")
    public ResponseEntity<Object> getCandidateProfile(HttpServletRequest request) {

        var candidateId = request.getAttribute("candidate_id");
        
        try {
            var profile = this.profileCandidateService.profileCandidate(UUID.fromString(candidateId.toString()));

            return ResponseEntity.ok().body(profile);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    

}
