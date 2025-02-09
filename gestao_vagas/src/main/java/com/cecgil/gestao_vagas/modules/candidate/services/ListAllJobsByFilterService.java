package com.cecgil.gestao_vagas.modules.candidate.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cecgil.gestao_vagas.modules.company.entities.JobEntity;
import com.cecgil.gestao_vagas.modules.company.repository.JobRepository;

@Service
public class ListAllJobsByFilterService {

    @Autowired
    private JobRepository jobRepository;

    public List<JobEntity> filterJob(String filter) {
        return this.jobRepository.findByDescriptionContainingIgnoreCase
        (filter);
    }
    
}
