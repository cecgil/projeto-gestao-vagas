package com.cecgil.gestao_vagas.modules.candidate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cecgil.gestao_vagas.exceptions.UserFoundException;
import com.cecgil.gestao_vagas.modules.candidate.entity.CandidateEntity;
import com.cecgil.gestao_vagas.modules.candidate.repository.CandidateRepository;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CandidateEntity creteCandidate(CandidateEntity candidateEntity) {
        this.candidateRepository
        .findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
        .ifPresent((user) -> {
            throw new UserFoundException();
        });

        var password = this.passwordEncoder.encode(candidateEntity.getPassword());
        candidateEntity.setPassword(password);

        return this.candidateRepository.save(candidateEntity);
    }
    
}
