package com.meva.finance.repository;

import com.meva.finance.model.Family;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FamilyRepository extends JpaRepository<Family, Long> {
    Optional<Family> findById(@NotNull Long idFamily);
}
