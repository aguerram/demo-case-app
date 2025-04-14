package fr.inventivit.caseapp.infrastructure.adapter.rest.cases;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.inventivit.caseapp.application.port.in.CaseMutationUseCase;
import fr.inventivit.caseapp.application.port.in.CaseQueryUseCase;
import fr.inventivit.caseapp.domain.model.Case;
import fr.inventivit.caseapp.infrastructure.adapter.rest.cases.dto.CaseDTO;
import fr.inventivit.caseapp.infrastructure.adapter.rest.cases.dto.CreateCaseRequest;
import fr.inventivit.caseapp.infrastructure.adapter.rest.cases.dto.UpdateCaseRequest;
import fr.inventivit.caseapp.infrastructure.mapper.CaseMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CasesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @SuppressWarnings("unused")
    @MockitoBean
    private CaseQueryUseCase caseQueryUseCase;

    @SuppressWarnings("unused")
    @MockitoBean
    private CaseMutationUseCase caseMutationUseCase;

    @SuppressWarnings("unused")
    @MockitoBean
    private CaseMapper caseMapper;

    @Test
    void getCaseById_withExistingId_shouldReturnCase() throws Exception {
        // Given
        Long caseId = 1L;
        Case caseModel = new Case(1L, "Test Case", "Test Description", LocalDateTime.now(), LocalDateTime.now());
        CaseDTO caseDTO = new CaseDTO(1L, "Test Case", "Test Description", LocalDateTime.now(), LocalDateTime.now());

        when(caseQueryUseCase.getCaseById(caseId)).thenReturn(Optional.of(caseModel));
        when(caseMapper.toCaseDTO(caseModel)).thenReturn(caseDTO);

        // When & Then
        mockMvc.perform(get("/cases/{caseId}", caseId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(caseId))
                .andExpect(jsonPath("$.title").value("Test Case"));
    }

    @Test
    void getCaseById_withNonExistingId_shouldReturn404() throws Exception {
        // Given
        Long caseId = 999L;
        when(caseQueryUseCase.getCaseById(caseId)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/cases/{caseId}", caseId))
                .andExpect(status().isNotFound());
    }

    @Test
    void createCase_withValidRequest_shouldReturnCreatedCase() throws Exception {
        // Given
        CreateCaseRequest request = new CreateCaseRequest("New Case", "New Description");
        Case caseModel = new Case(null, "New Case", "New Description", null, null);
        Case createdCase = new Case(1L, "New Case", "New Description", LocalDateTime.now(), LocalDateTime.now());
        CaseDTO caseDTO = new CaseDTO(1L, "New Case", "New Description", LocalDateTime.now(), LocalDateTime.now());

        when(caseMapper.createCaseRequestToModel(request)).thenReturn(caseModel);
        when(caseMutationUseCase.createNewCase(caseModel)).thenReturn(createdCase);
        when(caseMapper.toCaseDTO(createdCase)).thenReturn(caseDTO);

        // When & Then
        mockMvc.perform(post("/cases")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("New Case"));
    }

    @Test
    void updateCase_withValidRequest_shouldReturnUpdatedCase() throws Exception {
        // Given
        Long caseId = 1L;
        UpdateCaseRequest request = new UpdateCaseRequest("Updated Case", "Updated Description");
        Case caseModel = new Case(null, "Updated Case", "Updated Description", null, null);
        Case updatedCase = new Case(1L, "Updated Case", "Updated Description", LocalDateTime.now(), LocalDateTime.now());
        CaseDTO caseDTO = new CaseDTO(1L, "Updated Case", "Updated Description", LocalDateTime.now(), LocalDateTime.now());

        when(caseMapper.updateCaseRequestToModel(request)).thenReturn(caseModel);
        when(caseMutationUseCase.updateCase(any(Case.class))).thenReturn(updatedCase);
        when(caseMapper.toCaseDTO(updatedCase)).thenReturn(caseDTO);

        // When & Then
        mockMvc.perform(put("/cases/{caseId}", caseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Case"));
    }

    @Test
    void deleteCase_withExistingId_shouldReturn204() throws Exception {
        // Given
        Long caseId = 1L;
        doNothing().when(caseMutationUseCase).deleteCase(caseId);

        // When & Then
        mockMvc.perform(delete("/cases/{caseId}", caseId))
                .andExpect(status().isNoContent());

        verify(caseMutationUseCase).deleteCase(caseId);
    }
}