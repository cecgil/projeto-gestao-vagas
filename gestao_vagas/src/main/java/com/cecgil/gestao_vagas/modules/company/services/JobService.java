package com.cecgil.gestao_vagas.modules.company.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cecgil.gestao_vagas.exceptions.CompanyNotFoundException;
import com.cecgil.gestao_vagas.modules.company.entities.JobEntity;
import com.cecgil.gestao_vagas.modules.company.repository.CompanyRespository;
import com.cecgil.gestao_vagas.modules.company.repository.JobRepository;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CompanyRespository companyRespository;

    public JobEntity createService(JobEntity jobEntity) {
        companyRespository.findById(jobEntity.getCompanyId()).orElseThrow(() -> {
            throw new CompanyNotFoundException();
        });
        return this.jobRepository.save(jobEntity);
    }
    
}
