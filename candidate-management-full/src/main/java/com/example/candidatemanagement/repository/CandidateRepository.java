package com.example.candidatemanagement.repository;

import com.example.candidatemanagement.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Optional;

public interface CandidateRepository extends JpaRepository<Candidate, Long>,
        JpaSpecificationExecutor<Candidate> {
    Optional<Candidate> findByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCase(String email);
}
