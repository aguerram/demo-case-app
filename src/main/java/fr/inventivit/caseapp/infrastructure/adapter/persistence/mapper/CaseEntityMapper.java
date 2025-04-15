package fr.inventivit.caseapp.infrastructure.adapter.persistence.mapper;

import fr.inventivit.caseapp.domain.model.Case;
import fr.inventivit.caseapp.infrastructure.adapter.persistence.entity.CaseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CaseEntityMapper {
    @Mapping(source = "id", target = "caseId")
    CaseEntity toCaseEntity(Case caseModel);

    @Mapping(target = "id", source = "caseId")
    Case toCaseModel(CaseEntity caseEntity);
}
