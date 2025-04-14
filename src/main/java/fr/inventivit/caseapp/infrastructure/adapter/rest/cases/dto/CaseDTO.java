package fr.inventivit.caseapp.infrastructure.adapter.rest.cases.dto;

import java.time.LocalDateTime;

public record CaseDTO(
        Long id,
        String title,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
