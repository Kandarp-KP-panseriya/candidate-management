package com.example.candidatemanagement.service;

import com.example.candidatemanagement.dto.CandidateRequest;
import com.example.candidatemanagement.dto.CandidateResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CandidateService {
    CandidateResponse create(CandidateRequest request);
    CandidateResponse getById(Long id);
    CandidateResponse update(Long id, CandidateRequest request);
    void delete(Long id);

}
