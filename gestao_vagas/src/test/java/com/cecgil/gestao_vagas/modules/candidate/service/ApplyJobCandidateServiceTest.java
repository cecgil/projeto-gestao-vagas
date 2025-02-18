package com.cecgil.gestao_vagas.modules.candidate.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import com.cecgil.gestao_vagas.exceptions.JobNotFoundException;
import com.cecgil.gestao_vagas.exceptions.UserNotFoundException;
import com.cecgil.gestao_vagas.modules.candidate.entity.ApplyJobEntity;
import com.cecgil.gestao_vagas.modules.candidate.entity.CandidateEntity;
import com.cecgil.gestao_vagas.modules.candidate.repository.ApplyJobRepository;
import com.cecgil.gestao_vagas.modules.candidate.repository.CandidateRepository;
import com.cecgil.gestao_vagas.modules.candidate.services.ApplyJobCandidateService;
import com.cecgil.gestao_vagas.modules.company.entities.JobEntity;
import com.cecgil.gestao_vagas.modules.company.repository.JobRepository;

//instanciar a classe com mockito
@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateServiceTest {

    @InjectMocks
    private ApplyJobCandidateService applyJobCandidateService;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private ApplyJobRepository applyJobRepository;

    @Test
    @DisplayName("Should not be able to apply job with candidate not found")
    public void shouldNotBeAbleToApplyJobWithCandidateNotFound() {
        try {
            applyJobCandidateService.applyJob(null, null);
        } catch(Exception e) {
            assertThat(e).isInstanceOf(UserNotFoundException.class);
        }
        
    }

    @Test
    public void shouldNotBeAbleToApplyJobWithJobNotFound() {
        var candidateId = UUID.randomUUID();

        var candidate = new CandidateEntity();
        candidate.setId(candidateId);

        when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate));

        try {
            applyJobCandidateService.applyJob(candidateId, null);
        } catch(Exception e) {
            assertThat(e).isInstanceOf(JobNotFoundException.class);
        }
    }

    public void shouldbeAbleToCreateNewApplyJob() {
        var candidateId = UUID.randomUUID();

        var jobId = UUID.randomUUID();

        var applyJob = ApplyJobEntity.builder()
        .candidateId(candidateId)
        .jobId(jobId)
        .build();

        var applyJobCreated = ApplyJobEntity.builder().id(UUID.randomUUID()).build();

        applyJob.setId(UUID.randomUUID());

        when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(new CandidateEntity()));
        when(jobRepository.findById(jobId)).thenReturn(Optional.of(new JobEntity()));

        when(applyJobRepository.save(applyJob)).thenReturn(applyJobCreated);

        var result = applyJobCandidateService.applyJob(candidateId, jobId);

        assertThat(result).hasFieldOrProperty("id");
    }
    
}
