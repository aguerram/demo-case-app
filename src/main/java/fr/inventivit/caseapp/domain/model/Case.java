package fr.inventivit.caseapp.domain.model;

import lombok.Builder;
import lombok.With;

import java.time.LocalDateTime;

@With
@Builder
public record Case(
        Long id,
        String title,
        String description,
        LocalDateTime creationDate,
        LocalDateTime lastUpdateDate
) {
}
