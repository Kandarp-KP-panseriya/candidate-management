package com.example.candidatemanagement.controller;

import com.example.candidatemanagement.dto.*;
import com.example.candidatemanagement.service.CandidateService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/candidates")
@Validated
public class CandidateController {
    private final CandidateService service;

    public CandidateController(CandidateService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CandidateResponse> create(@Valid @RequestBody CandidateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @GetMapping("/{id}")
    public CandidateResponse getById(@PathVariable @Positive Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public CandidateResponse update(@PathVariable @Positive Long id,
                                    @Valid @RequestBody CandidateRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Positive Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public Page<CandidateResponse> search(
        @RequestParam(required = false) String q,
        @RequestParam(required = false) @DecimalMin("0.0") Double minExperience,
        @RequestParam(required = false) @DecimalMin("0.0") Double maxExperience,
        @RequestParam(required = false) String company,
        @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return service.search(q, minExperience, maxExperience, company, pageable);
    }
}
