package fr.inventivit.caseapp.infrastructure.mapper;

import fr.inventivit.caseapp.domain.model.Case;
import fr.inventivit.caseapp.infrastructure.adapter.rest.cases.dto.CaseDTO;
import fr.inventivit.caseapp.infrastructure.adapter.rest.cases.dto.CreateCaseRequest;
import fr.inventivit.caseapp.infrastructure.adapter.rest.cases.dto.UpdateCaseRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CaseMapper {
    @Mapping(source = "creationDate", target = "createdAt")
    @Mapping(source = "lastUpdateDate", target = "updatedAt")
    CaseDTO toCaseDTO(Case caseModel);

    Case createCaseRequestToModel(CreateCaseRequest createCaseRequest);

    Case updateCaseRequestToModel(UpdateCaseRequest updateCaseRequest);
}
