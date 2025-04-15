package fr.inventivit.caseapp.infrastructure.adapter.rest.cases;

import fr.inventivit.caseapp.application.port.in.CaseMutationUseCase;
import fr.inventivit.caseapp.application.port.in.CaseQueryUseCase;
import fr.inventivit.caseapp.domain.exception.CaseNotFoundException;
import fr.inventivit.caseapp.domain.model.Case;
import fr.inventivit.caseapp.infrastructure.adapter.rest.cases.dto.CaseDTO;
import fr.inventivit.caseapp.infrastructure.adapter.rest.cases.dto.CreateCaseRequest;
import fr.inventivit.caseapp.infrastructure.adapter.rest.cases.dto.UpdateCaseRequest;
import fr.inventivit.caseapp.infrastructure.mapper.CaseMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Cases", description = "The Cases API")
class CasesController {
    private final CaseQueryUseCase caseQueryUseCase;
    private final CaseMutationUseCase caseMutationUseCase;
    private final CaseMapper caseMapper;

    @Operation(summary = "Get a case by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the case",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CaseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Case not found")
    })
    @GetMapping("/{caseId:\\d+}")
    CaseDTO getCaseById(
            @Parameter(description = "ID of the case to be retrieved", example = "1") @PathVariable Long caseId
    ) {
        log.debug("Handle mapping to get case by id {}", caseId);
        Optional<Case> caseOptional = caseQueryUseCase.getCaseById(caseId);
        if (caseOptional.isEmpty()) {
            log.debug("Case with id {} not found", caseId);
            throw new CaseNotFoundException(caseId);
        }
        return caseMapper.toCaseDTO(caseOptional.get());
    }

    @Operation(summary = "Create a new case")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Case created successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CaseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CaseDTO createCase(
            @Parameter(description = "Case to be created", example = """
                    {
                        "title":"Title",
                        "description":"Description"
                    }
                    """)
            @Valid @RequestBody CreateCaseRequest createCaseRequest) {
        log.debug("Handle mapping to create case {}", createCaseRequest);
        Case caseModel = caseMapper.createCaseRequestToModel(createCaseRequest);
        Case createdCase = caseMutationUseCase.createNewCase(caseModel);
        log.debug("Case created with id {}", createdCase.id());
        return caseMapper.toCaseDTO(createdCase);
    }

    @Operation(summary = "Update an existing case")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Case updated successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CaseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Case not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{caseId:\\d+}")
    CaseDTO updateCase(
            @Parameter(description = "ID of the case to be updated", example = "1") @PathVariable Long caseId,
            @Parameter(description = "Updated case information", example = """
                    {
                        "title":"New title",
                        "description":"New description"
                    }
                    """)
            @Valid @RequestBody UpdateCaseRequest updateCaseRequest
    ) {
        log.debug("Handle mapping to update case id {} with new values {}", caseId, updateCaseRequest);
        Case caseModel = caseMapper.updateCaseRequestToModel(updateCaseRequest);
        Case updateCase = caseMutationUseCase.updateCase(caseModel.withId(caseId));
        if (updateCase == null) {
            log.debug("Case with id {} not found", caseId);
            throw new CaseNotFoundException(caseId);
        }
        log.debug("Case {} updated with new values {}", caseId, updateCase);
        return caseMapper.toCaseDTO(updateCase);
    }

    @Operation(summary = "Delete a case")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Case deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Case not found")
    })
    @DeleteMapping("/{caseId:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCase(@Parameter(description = "ID of the case to be deleted", example = "1") @PathVariable Long caseId) {
        log.debug("Handle mapping to delete case {}", caseId);
        caseMutationUseCase.deleteCase(caseId);
        log.debug("Case with id {} deleted", caseId);
    }
}
