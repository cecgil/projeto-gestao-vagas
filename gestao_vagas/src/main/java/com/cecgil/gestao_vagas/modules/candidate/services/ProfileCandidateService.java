package com.cecgil.gestao_vagas.modules.candidate.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cecgil.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import com.cecgil.gestao_vagas.modules.candidate.repository.CandidateRepository;

@Service
public class ProfileCandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    public ProfileCandidateResponseDTO profileCandidate(UUID idCandidate) {

        var candidate = this.candidateRepository.findById(idCandidate)
                                                .orElseThrow(() -> {
                                                    throw new UsernameNotFoundException("User not found");
                                                });
        var candidateDTO = ProfileCandidateResponseDTO.builder()
                                                    .description(candidate.getDescription())
                                                    .email(candidate.getEmail())
                                                    .name(candidate.getName())
                                                    .username(candidate.getUsername())
                                                    .id(candidate.getId())
                                                    .build();
        return candidateDTO;
    }
    
}
