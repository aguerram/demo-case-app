package fr.inventivit.caseapp.infrastructure.adapter.persistence.repository;

import fr.inventivit.caseapp.application.port.out.CaseRepository;
import fr.inventivit.caseapp.domain.model.Case;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
@Slf4j
public class CaseRepositoryImpl implements CaseRepository {
    private final JpaCaseRepository jpaCaseRepository;

    @Override
    public Case createNewCase(Case caseToCreate) {
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
        return Optional.empty();
    }
}
