package fr.inventivit.caseapp.application.service;

import fr.inventivit.caseapp.application.port.in.CaseMutationUseCase;
import fr.inventivit.caseapp.application.port.in.CaseQueryUseCase;
import fr.inventivit.caseapp.application.port.out.CaseRepository;
import fr.inventivit.caseapp.domain.model.Case;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CaseService implements CaseQueryUseCase, CaseMutationUseCase {
    private final CaseRepository caseRepository;

    @Override
    public Case createNewCase(Case caseToCreate) {
        log.info("Creating new case: {}", caseToCreate);
        Assert.notNull(caseToCreate, "Case to create must not be null");
        Case newCase = caseRepository.createNewCase(caseToCreate);
        log.info("New case created under ID: {}", newCase.id());
        return newCase;
    }

    @Override
    public Case updateCase(Case caseToUpdate) {
        log.info("Updating case: {}", caseToUpdate);
        Assert.notNull(caseToUpdate, "Case to update must not be null");
        Assert.notNull(caseToUpdate.id(), "Case ID must not be null");

        Case existingCase = caseRepository.getCaseById(caseToUpdate.id())
                .orElse(null);
        if (existingCase == null) {
            log.info("Case with ID {} not found, abort", caseToUpdate.id());
            return null;
        }

        if (StringUtils.hasText(caseToUpdate.title())) {
            existingCase = existingCase.withTitle(caseToUpdate.title());
        }
        if (StringUtils.hasText(caseToUpdate.description())) {
            existingCase = existingCase.withDescription(caseToUpdate.description());
        }

        Case updatedCase = caseRepository.updateCase(existingCase);
        log.info("Case with ID {} updated", caseToUpdate.id());
        return updatedCase;
    }

    @Override
    public void deleteCase(Long caseId) {
        log.info("Deleting case with ID: {}", caseId);
        Assert.notNull(caseId, "Case ID must not be null");
        caseRepository.deleteCase(caseId);
    }

    @Override
    public Optional<Case> getCaseById(Long caseId) {
        log.info("Fetching case with ID: {}", caseId);
        Assert.notNull(caseId, "Case ID must not be null");
        Optional<Case> caseOptional = caseRepository.getCaseById(caseId);
        if (caseOptional.isPresent()) {
            log.info("Case found: {}", caseOptional.get());
            return caseOptional;
        }
        log.info("Case with ID {} not found", caseId);
        return Optional.empty();
    }
}
