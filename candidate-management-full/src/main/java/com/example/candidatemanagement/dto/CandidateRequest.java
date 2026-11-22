package com.example.candidatemanagement.dto;

import jakarta.validation.constraints.*;

public record CandidateRequest(
    @NotBlank @Size(max = 80) String firstName,
    @NotBlank @Size(max = 80) String lastName,
    @NotBlank @Email @Size(max = 160) String email,
    @Pattern(regexp = "^[+0-9() .-]{7,25}$", message = "must be a valid phone number")
    String phone,
    @Size(max = 500) String skills,
    @NotNull @DecimalMin("0.0") @DecimalMax("60.0") Double experienceYears,
    @Size(max = 120) String currentCompany,
    @Min(0) @Max(365) Integer noticePeriodDays
) {}
