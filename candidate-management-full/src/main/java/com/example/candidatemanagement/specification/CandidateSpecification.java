package com.example.candidatemanagement.specification;

import com.example.candidatemanagement.entity.Candidate;
import org.springframework.data.jpa.domain.Specification;

public final class CandidateSpecification {
    private CandidateSpecification() {}

    public static Specification<Candidate> hasText(String term) {
        return (root, query, cb) -> {
            if (term == null || term.isBlank()) return cb.conjunction();
            String pattern = "%" + term.trim().toLowerCase() + "%";
            return cb.or(
                cb.like(cb.lower(root.get("firstName")), pattern),
                cb.like(cb.lower(root.get("lastName")), pattern),
                cb.like(cb.lower(root.get("email")), pattern),
                cb.like(cb.lower(root.get("skills")), pattern),
                cb.like(cb.lower(root.get("currentCompany")), pattern)
            );
        };
    }

    public static Specification<Candidate> experienceAtLeast(Double min) {
        return (root, query, cb) -> min == null ? cb.conjunction()
            : cb.greaterThanOrEqualTo(root.get("experienceYears"), min);
    }

    public static Specification<Candidate> experienceAtMost(Double max) {
        return (root, query, cb) -> max == null ? cb.conjunction()
            : cb.lessThanOrEqualTo(root.get("experienceYears"), max);
    }

    public static Specification<Candidate> companyContains(String company) {
        return (root, query, cb) -> company == null || company.isBlank() ? cb.conjunction()
            : cb.like(cb.lower(root.get("currentCompany")), "%" + company.trim().toLowerCase() + "%");
    }
}
