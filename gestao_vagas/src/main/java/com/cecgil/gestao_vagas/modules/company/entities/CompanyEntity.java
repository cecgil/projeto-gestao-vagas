package com.cecgil.gestao_vagas.modules.company.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity(name="company")
@Data
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotBlank
    @Pattern(regexp = "\\S+", message = "O campo username não deve conter espaço")
    private String username;

    @Email(message = "O campo e-mail deve conter um e-mail valido")
    private String email;

    private String name;
    private String website;
    private String description;
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;

    
    
}
