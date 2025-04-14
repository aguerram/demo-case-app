package fr.inventivit.caseapp.application.service;

import fr.inventivit.caseapp.application.port.in.CaseMutationUseCase;
import fr.inventivit.caseapp.application.port.in.CaseQueryUseCase;
import fr.inventivit.caseapp.domain.model.Case;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CaseService implements CaseQueryUseCase, CaseMutationUseCase {
    @Override
    public Case createNewCase(Case caseToUpdate) {
        return null;
    }

    @Override
    public Case updateCase(Case caseToUpdate) {
        return null;
    }

    @Override
    public void deleteCase(Long caseId) {

    }

    @Override
    public Optional<Case> getCaseById(Long caseId) {
        return null;
    }
}
