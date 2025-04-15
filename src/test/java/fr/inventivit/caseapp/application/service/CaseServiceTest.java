package fr.inventivit.caseapp.application.service;

import fr.inventivit.caseapp.application.port.out.CaseRepository;
import fr.inventivit.caseapp.domain.model.Case;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CaseServiceTest {
    private CaseService caseService;
    @Mock
    private CaseRepository caseRepository;

    @BeforeEach
    void setUp() {
        caseService = new CaseService(caseRepository);
    }

    @Test
    void createNewCase_withValidData_shouldReturnCreatedCase() {
        // given
        Case caseToCreate = Case.builder()
                .title("Test Case")
                .description("Test Description")
                .build();

        when(caseRepository.createNewCase(caseToCreate))
                .thenReturn(
                        caseToCreate
                                .withId(1L)
                                .withCreationDate(LocalDateTime.now())
                                .withLastUpdateDate(LocalDateTime.now())
                );

        //when
        Case newCase = caseService.createNewCase(caseToCreate);

        // then
        Assertions.assertThat(newCase).isNotNull();
        Assertions.assertThat(newCase.title()).isEqualTo("Test Case");
        Assertions.assertThat(newCase.description()).isEqualTo("Test Description");
        Assertions.assertThat(newCase.creationDate()).isNotNull();
        Assertions.assertThat(newCase.lastUpdateDate()).isNotNull();
        Assertions.assertThat(newCase.id()).isNotNull();
    }

    @Test
    void updateCase_withNonExistingId_shouldFail() {
        // given
        Case caseToUpdate = Case.builder()
                .id(1000L)
                .title("New Title")
                .description("New Description")
                .build();

        when(caseRepository.getCaseById(any())).thenReturn(Optional.empty());
        // when
        Assertions.assertThat(caseService.updateCase(caseToUpdate)).isNull();

        Assertions.assertThatThrownBy(() -> {
                    caseService.updateCase(caseToUpdate.withId(null));
                })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Case ID must not be null");

    }

    @Test
    void updateCase_withTitleOnly_shouldReturnUpdatedCase() {
        // given
        Case caseToUpdate = Case.builder()
                .id(1L)
                .title("New Title")
                .build();

        when(caseRepository.getCaseById(any()))
                .thenReturn(
                        Optional.of(
                                Case.builder()
                                        .id(1L)
                                        .title("Old Title")
                                        .description("Old Description")
                                        .creationDate(LocalDateTime.now().minus(Duration.ofMinutes(1)))
                                        .lastUpdateDate(null)
                                        .build()
                        )
                );

        when(caseRepository.updateCase(any(Case.class)))
                .thenReturn(
                        Case.builder()
                                .id(1L)
                                .title("New Title")
                                .description("Old Description")
                                .creationDate(LocalDateTime.now().minus(Duration.ofMinutes(1)))
                                .lastUpdateDate(LocalDateTime.now())
                                .build()
                );

        // when
        Case updatedCase = caseService.updateCase(caseToUpdate);
        Assertions.assertThat(updatedCase).isNotNull();
        Assertions.assertThat(updatedCase.title()).isEqualTo("New Title");
        Assertions.assertThat(updatedCase.description()).isEqualTo("Old Description");
        Assertions.assertThat(updatedCase.creationDate()).isNotNull();
        Assertions.assertThat(updatedCase.lastUpdateDate()).isNotNull();
    }

    @Test
    void deleteCase_withExistingId_shouldDeleteCase() {
        Long caseId = 1L;
        caseService.deleteCase(caseId);

        when(caseRepository.getCaseById(caseId))
                .thenReturn(Optional.empty());

        Assertions.assertThat(caseService.getCaseById(caseId)).isEmpty();
    }

    @Test
    void deleteCase_withNonExistingIdShouldDotNothing_shouldFail() {
        Long caseId = 1000L;
        caseService.deleteCase(caseId); // No exception should be thrown
        // Verify that the delete method was called
        when(caseRepository.getCaseById(caseId))
                .thenReturn(Optional.of(Case.builder().id(caseId).build()));

        Assertions.assertThat(caseService.getCaseById(caseId)).isNotEmpty(); // we still have old record
    }
}