package fr.inventivit.caseapp.infrastructure.adapter.rest.cases.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCaseRequest(
        @NotBlank @Size(max = 255) String title,
        @NotBlank @Size(max = 2056) String description
) {
}
