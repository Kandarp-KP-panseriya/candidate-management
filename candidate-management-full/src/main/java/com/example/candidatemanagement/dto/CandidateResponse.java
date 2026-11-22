package com.example.candidatemanagement.dto;

import java.time.LocalDateTime;

public record CandidateResponse(
    Long id, String firstName, String lastName, String email, String phone,
    String skills, Double experienceYears, String currentCompany,
    Integer noticePeriodDays, LocalDateTime createdAt, LocalDateTime updatedAt
) {}
