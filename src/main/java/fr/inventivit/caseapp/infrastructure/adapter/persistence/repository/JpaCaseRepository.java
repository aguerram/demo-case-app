package fr.inventivit.caseapp.infrastructure.adapter.persistence.repository;

import fr.inventivit.caseapp.infrastructure.adapter.persistence.entity.CaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface JpaCaseRepository extends JpaRepository<CaseEntity, Long> {
}
