package com.cecgil.gestao_vagas.modules.candidate.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

import com.cecgil.gestao_vagas.exceptions.UserNotFoundException;
import com.cecgil.gestao_vagas.modules.candidate.repository.CandidateRepository;
import com.cecgil.gestao_vagas.modules.candidate.services.ApplyJobCandidateService;
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

    @Test
    @DisplayName("Should not be able to apply job with candidate not found")
    public void shouldNotBeAbleToApplyJobWithCandidateNotFound() {
        try {
            applyJobCandidateService.applyJob(null, null);
        } catch(Exception e) {
            assertThat(e).isInstanceOf(UserNotFoundException.class);
        }
        
    }
    
}
