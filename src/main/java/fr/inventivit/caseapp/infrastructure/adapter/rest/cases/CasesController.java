package fr.inventivit.caseapp.infrastructure.adapter.rest.cases;

import fr.inventivit.caseapp.application.port.in.CaseMutationUseCase;
import fr.inventivit.caseapp.application.port.in.CaseQueryUseCase;
import fr.inventivit.caseapp.domain.exception.CaseNotFoundException;
import fr.inventivit.caseapp.domain.model.Case;
import fr.inventivit.caseapp.infrastructure.adapter.rest.cases.dto.CaseDTO;
import fr.inventivit.caseapp.infrastructure.adapter.rest.cases.dto.CreateCaseRequest;
import fr.inventivit.caseapp.infrastructure.adapter.rest.cases.dto.UpdateCaseRequest;
import fr.inventivit.caseapp.infrastructure.mapper.CaseMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/cases")
@Validated
@RequiredArgsConstructor
@Slf4j
class CasesController {
    private final CaseQueryUseCase caseQueryUseCase;
    private final CaseMutationUseCase caseMutationUseCase;
    private final CaseMapper caseMapper;

    @GetMapping("/{caseId:\\d+}")
    CaseDTO getCaseById(@PathVariable Long caseId) {
        log.debug("Handle mapping to get case by id {}", caseId);
        Optional<Case> caseOptional = caseQueryUseCase.getCaseById(caseId);
        if (caseOptional.isEmpty()) {
            log.debug("Case with id {} not found", caseId);
            throw new CaseNotFoundException(caseId);
        }
        return caseMapper.toCaseDTO(caseOptional.get());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CaseDTO createCase(@Valid @RequestBody CreateCaseRequest createCaseRequest) {
        log.debug("Handle mapping to create case {}", createCaseRequest);
        Case caseModel = caseMapper.createCaseRequestToModel(createCaseRequest);
        Case createdCase = caseMutationUseCase.createNewCase(caseModel);
        log.debug("Case created with id {}", createdCase.id());
        return caseMapper.toCaseDTO(createdCase);
    }

    @PutMapping("/{caseId:\\d+}")
    CaseDTO updateCase(@PathVariable Long caseId, @Valid @RequestBody UpdateCaseRequest updateCaseRequest) {
        log.debug("Handle mapping to update case id {} with new values {}", caseId, updateCaseRequest);
        Case caseModel = caseMapper.updateCaseRequestToModel(updateCaseRequest);
        Case updateCase = caseMutationUseCase.updateCase(caseModel.withId(caseId));
        log.debug("Case {} updated with new values {}", caseId, updateCase);
        return caseMapper.toCaseDTO(updateCase);
    }

    @DeleteMapping("/{caseId:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCase(@PathVariable Long caseId) {
        log.debug("Handle mapping to delete case {}", caseId);
        caseMutationUseCase.deleteCase(caseId);
        log.debug("Case with id {} deleted", caseId);
    }
}
