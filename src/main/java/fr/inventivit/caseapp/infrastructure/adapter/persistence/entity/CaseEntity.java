package fr.inventivit.caseapp.infrastructure.adapter.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "cases")
@EntityListeners(AuditingEntityListener.class)
public class CaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long caseId;

    @Size(min = 1, max = 255)
    @Column(nullable = false)
    private String title;

    @Size(min = 1, max = 2056)
    @Column(nullable = false, length = 2056)
    private String description;

    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime creationDate;

    @LastModifiedDate
    private LocalDateTime lastUpdateDate;
}
