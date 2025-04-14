package fr.inventivit.caseapp.domain.model;

import lombok.With;

import java.time.LocalDateTime;

@With
public record Case(
        Long id,
        String title,
        String description,
        LocalDateTime creationDate,
        LocalDateTime lastUpdateDate
) {
}
