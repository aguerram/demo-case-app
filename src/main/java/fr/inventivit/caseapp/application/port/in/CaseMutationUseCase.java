package fr.inventivit.caseapp.application.port.in;

import fr.inventivit.caseapp.domain.model.Case;

public interface CaseMutationUseCase {
    Case createNewCase(Case caseToCreate);

    /**
     * Update an existing case
     * @param caseToUpdate
     * @return the updated case or null if the case does not exist
     */
    Case updateCase(Case caseToUpdate);

    void deleteCase(Long caseId);
}
