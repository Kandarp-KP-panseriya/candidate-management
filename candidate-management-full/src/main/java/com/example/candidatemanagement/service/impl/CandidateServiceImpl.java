package com.example.candidatemanagement.service.impl;

import com.example.candidatemanagement.dto.*;
import com.example.candidatemanagement.entity.Candidate;
import com.example.candidatemanagement.exception.*;
import com.example.candidatemanagement.repository.CandidateRepository;
import com.example.candidatemanagement.service.CandidateService;
import com.example.candidatemanagement.specification.CandidateSpecification;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CandidateServiceImpl implements CandidateService {
    private final CandidateRepository repository;

    public CandidateServiceImpl(CandidateRepository repository) {
        this.repository = repository;
    }

    @Override
    public CandidateResponse create(CandidateRequest request) {
        if (repository.existsByEmailIgnoreCase(request.email().trim())) {
            throw new DuplicateResourceException("A candidate with this email already exists");
        }
        Candidate candidate = new Candidate();
        apply(candidate, request);
        return toResponse(repository.save(candidate));
    }

    @Override
    @Transactional(readOnly = true)
    public CandidateResponse getById(Long id) {
        return toResponse(findCandidate(id));
    }

    @Override
    public CandidateResponse update(Long id, CandidateRequest request) {
        Candidate candidate = findCandidate(id);
        repository.findByEmailIgnoreCase(request.email().trim())
            .filter(existing -> !existing.getId().equals(id))
            .ifPresent(existing -> { throw new DuplicateResourceException("A candidate with this email already exists"); });
        apply(candidate, request);
        return toResponse(repository.save(candidate));
    }

    @Override
    public void delete(Long id) {
        repository.delete(findCandidate(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CandidateResponse> search(String q, Double minExperience, Double maxExperience,
                                          String company, Pageable pageable) {
        if (minExperience != null && maxExperience != null && minExperience > maxExperience) {
            throw new IllegalArgumentException("minExperience must be less than or equal to maxExperience");
        }
        Specification<Candidate> spec = Specification
            .where(CandidateSpecification.hasText(q))
            .and(CandidateSpecification.experienceAtLeast(minExperience))
            .and(CandidateSpecification.experienceAtMost(maxExperience))
            .and(CandidateSpecification.companyContains(company));
        return repository.findAll(spec, pageable).map(this::toResponse);
    }

    private Candidate findCandidate(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with id: " + id));
    }

    private void apply(Candidate candidate, CandidateRequest request) {
        candidate.setFirstName(request.firstName().trim());
        candidate.setLastName(request.lastName().trim());
        candidate.setEmail(request.email().trim().toLowerCase());
        candidate.setPhone(request.phone());
        candidate.setSkills(request.skills());
        candidate.setExperienceYears(request.experienceYears());
        candidate.setCurrentCompany(request.currentCompany());
        candidate.setNoticePeriodDays(request.noticePeriodDays());
    }

    private CandidateResponse toResponse(Candidate c) {
        return new CandidateResponse(c.getId(), c.getFirstName(), c.getLastName(), c.getEmail(),
            c.getPhone(), c.getSkills(), c.getExperienceYears(), c.getCurrentCompany(),
            c.getNoticePeriodDays(), c.getCreatedAt(), c.getUpdatedAt());
    }
}
