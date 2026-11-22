package com.example.candidatemanagement.service;

import com.example.candidatemanagement.dto.CandidateRequest;
import com.example.candidatemanagement.entity.Candidate;
import com.example.candidatemanagement.exception.DuplicateResourceException;
import com.example.candidatemanagement.repository.CandidateRepository;
import com.example.candidatemanagement.service.impl.CandidateServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CandidateServiceImplTest {
    @Mock CandidateRepository repository;
    @InjectMocks CandidateServiceImpl service;

    @Test
    void createRejectsDuplicateEmail() {
        CandidateRequest request = new CandidateRequest("Asha", "Patel", "asha@example.com",
            "9876543210", "Java, Spring", 2.0, "Acme", 30);
        when(repository.existsByEmailIgnoreCase("asha@example.com")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> service.create(request));
        verify(repository, never()).save(any(Candidate.class));
    }

    @Test
    void getByIdReturnsCandidate() {
        Candidate candidate = new Candidate();
        candidate.setId(7L);
        candidate.setFirstName("Asha");
        candidate.setLastName("Patel");
        candidate.setEmail("asha@example.com");
        candidate.setExperienceYears(2.0);
        when(repository.findById(7L)).thenReturn(Optional.of(candidate));

        assertEquals("asha@example.com", service.
    (7L).email());
    }
}
