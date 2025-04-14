package fr.inventivit.caseapp.infrastructure.adapter.rest.cases.dto;

import jakarta.validation.constraints.Size;
import org.springframework.lang.Nullable;

public record UpdateCaseRequest(
        @Nullable @Size(max = 255) String title,
        @Nullable @Size(max = 2056) String description
) {
}
