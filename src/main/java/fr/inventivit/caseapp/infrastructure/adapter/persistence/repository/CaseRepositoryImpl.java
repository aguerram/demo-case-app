package fr.inventivit.caseapp.infrastructure.adapter.persistence.repository;

import fr.inventivit.caseapp.application.port.out.CaseRepository;
import fr.inventivit.caseapp.domain.model.Case;
import fr.inventivit.caseapp.infrastructure.adapter.persistence.entity.CaseEntity;
import fr.inventivit.caseapp.infrastructure.adapter.persistence.mapper.CaseEntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
@Slf4j
public class CaseRepositoryImpl implements CaseRepository {
    private final JpaCaseRepository jpaCaseRepository;
    private final CaseEntityMapper caseEntityMapper;

    @Override
    public Case createNewCase(Case caseToCreate) {
        log.debug("Creating new case: {}", caseToCreate);
        CaseEntity caseEntity = caseEntityMapper.toCaseEntity(caseToCreate);
        return caseEntityMapper.toCaseModel(jpaCaseRepository.save(caseEntity));
    }

    @Override
    public Case updateCase(Case caseToUpdate) {
        log.debug("Updating case: {}", caseToUpdate);
        CaseEntity caseEntity = caseEntityMapper.toCaseEntity(caseToUpdate);
        return caseEntityMapper.toCaseModel(jpaCaseRepository.save(caseEntity));
    }

    @Override
    public void deleteCase(Long caseId) {
        log.debug("Deleting case: {}", caseId);
        jpaCaseRepository.deleteById(caseId);
    }

    @Override
    public Optional<Case> getCaseById(Long caseId) {
        log.debug("Getting case: {}", caseId);
        return jpaCaseRepository.findById(caseId)
                .map(caseEntityMapper::toCaseModel);
    }
}
