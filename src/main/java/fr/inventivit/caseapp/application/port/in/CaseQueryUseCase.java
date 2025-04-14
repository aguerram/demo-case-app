package fr.inventivit.caseapp.application.port.in;

import fr.inventivit.caseapp.domain.model.Case;

import java.util.Optional;

public interface CaseQueryUseCase {
    Optional<Case> getCaseById(Long caseId);
}
