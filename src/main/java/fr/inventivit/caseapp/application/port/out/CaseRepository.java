package fr.inventivit.caseapp.application.port.out;

import fr.inventivit.caseapp.domain.model.Case;

import java.util.Optional;

public interface CaseRepository {
    Case createNewCase(Case caseToCreate);

    Case updateCase(Case caseToUpdate);

    void deleteCase(Long caseId);

    Optional<Case> getCaseById(Long caseId);
}
