package com.cecgil.gestao_vagas.modules.candidate.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cecgil.gestao_vagas.exceptions.JobNotFoundException;
import com.cecgil.gestao_vagas.exceptions.UserNotFoundException;
import com.cecgil.gestao_vagas.modules.candidate.entity.ApplyJobEntity;
import com.cecgil.gestao_vagas.modules.candidate.repository.ApplyJobRepository;
import com.cecgil.gestao_vagas.modules.candidate.repository.CandidateRepository;
import com.cecgil.gestao_vagas.modules.company.repository.JobRepository;

@Service
public class ApplyJobCandidateService {

    @Autowired
    private JobRepository jobRepository;
    
    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private ApplyJobRepository applyJobRepository;

    public ApplyJobEntity applyJob(UUID candidateId, UUID jobId) {
        this.candidateRepository.findById(candidateId).orElseThrow(() -> {
            throw new UserNotFoundException();
        });

        this.jobRepository.findById(jobId).orElseThrow(() -> {
            throw new JobNotFoundException();
        });

        var applyJob = ApplyJobEntity.builder()
        .candidateId(candidateId)
        .jobId(jobId).build();

        applyJob = applyJobRepository.save(applyJob);

        return applyJob;
    }
}
