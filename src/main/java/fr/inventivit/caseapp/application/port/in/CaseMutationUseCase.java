package fr.inventivit.caseapp.application.port.in;

import fr.inventivit.caseapp.domain.model.Case;

public interface CaseMutationUseCase {
    Case createNewCase(Case caseToUpdate);

    Case updateCase(Case caseToUpdate);

    void deleteCase(Long caseId);
}
