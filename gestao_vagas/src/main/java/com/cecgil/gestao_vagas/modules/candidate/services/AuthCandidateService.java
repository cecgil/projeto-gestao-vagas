package com.cecgil.gestao_vagas.modules.candidate.services;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cecgil.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDTO;
import com.cecgil.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDTO;
import com.cecgil.gestao_vagas.modules.candidate.repository.CandidateRepository;

@Service
public class AuthCandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${security.token.secret.candidate}")
    private String secretKey;

    public AuthCandidateResponseDTO authCandidate(AuthCandidateRequestDTO authCandidateRequestDTO) throws AuthenticationException {
        var candidate = this.candidateRepository.findByUsername(authCandidateRequestDTO.username())
            .orElseThrow(() -> {
                throw new UsernameNotFoundException("Username/password incorrect");
            });

        var passwordMatches = this.passwordEncoder
        .matches(authCandidateRequestDTO.password(), 
        candidate.getPassword());

        if(!passwordMatches) {
            throw new AuthenticationException();
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        var token = JWT.create()
                       .withIssuer("vagas")
                       .withSubject(candidate.getId().toString())
                       .withClaim("roles", Arrays.asList("candidate"))
                       .withExpiresAt(Instant.now().plus(Duration.ofMinutes(10)))
                       .sign(algorithm);

        var authCandidateResponse = AuthCandidateResponseDTO.builder()
        .acess_tokenn(token)
        .build();

        return authCandidateResponse;
    }
    
}
