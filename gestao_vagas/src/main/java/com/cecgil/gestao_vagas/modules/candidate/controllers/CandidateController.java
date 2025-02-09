package com.cecgil.gestao_vagas.modules.candidate.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cecgil.gestao_vagas.modules.candidate.entity.CandidateEntity;
import com.cecgil.gestao_vagas.modules.candidate.services.CandidateService;
import com.cecgil.gestao_vagas.modules.candidate.services.ListAllJobsByFilterService;
import com.cecgil.gestao_vagas.modules.candidate.services.ProfileCandidateService;
import com.cecgil.gestao_vagas.modules.company.entities.JobEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/candidate")
public class CandidateController {
    
    @Autowired
    private CandidateService candidateService;

    @Autowired
    private ProfileCandidateService profileCandidateService;

    @Autowired
    private ListAllJobsByFilterService listAllJobsByFilterService;

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
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResponseEntity<Object> getCandidateProfile(HttpServletRequest request) {

        var candidateId = request.getAttribute("candidate_id");
        
        try {
            var profile = this.profileCandidateService.profileCandidate(UUID.fromString(candidateId.toString()));

            return ResponseEntity.ok().body(profile);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/job")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Tag(name = "Candidate", description = "Informações do candidato")
    @Operation(summary = "Listagem de vagas disponiveis para o candidato", description = "Essa função é responsável por listar todas as vagas disponiveis, por filtro")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(array = @ArraySchema(schema = @Schema(implementation = JobEntity.class)))
        })
    })
    @SecurityRequirement(name = "jwt_auth")
    public List<JobEntity> getListJobFilter(@RequestParam String filter) {
        return this.listAllJobsByFilterService.filterJob(filter);
    }
    
    

}
