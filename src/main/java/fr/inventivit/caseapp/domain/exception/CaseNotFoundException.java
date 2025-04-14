package fr.inventivit.caseapp.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CaseNotFoundException extends ResponseStatusException {
    public CaseNotFoundException(Long caseID) {
        super(HttpStatus.NOT_FOUND, String.format("Case with id %d not found", caseID));
    }
}
