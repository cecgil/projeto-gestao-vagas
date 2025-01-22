package com.cecgil.gestao_vagas.modules.company.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cecgil.gestao_vagas.modules.company.entities.JobEntity;
import com.cecgil.gestao_vagas.modules.company.repository.JobRepository;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public JobEntity createService(JobEntity jobEntity) {
        return this.jobRepository.save(jobEntity);
    }
    
}
