
package com.ivpulse.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.ConstraintViolationException;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@RestControllerAdvice
public class ApiErrorAdvice {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> onStatus(ResponseStatusException ex) {
        // Compute a non-null message (reason -> ProblemDetail.title -> HttpStatus reason -> generic)
        String message = ex.getReason();
        try {
            if (message == null && ex.getBody() != null && ex.getBody().getTitle() != null) {
                message = ex.getBody().getTitle(); // Problem Details title (Boot 3 / Spring 6)
            }
        } catch (Exception ignore) { /* defensive */ }

        if (message == null && ex.getStatusCode() instanceof HttpStatus hs) {
            message = hs.getReasonPhrase();
        }
        if (message == null) {
            message = "HTTP " + ex.getStatusCode().value();
        }

        // Build body with explicit types (no Map.of to avoid null/type inference issues)
        Map<String, Object> inner = new LinkedHashMap<>();
        inner.put("code", String.valueOf(ex.getStatusCode().value()));
        inner.put("message", message);

        Map<String, Object> body = new HashMap<>();
        body.put("error", inner);

        return ResponseEntity.status(ex.getStatusCode()).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> onValidation(MethodArgumentNotValidException ex) {
        List<Map<String, Object>> errs = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(fe -> {
            Map<String, Object> e = new LinkedHashMap<>();
            e.put("field", fe.getField());
            e.put("message", Optional.ofNullable(fe.getDefaultMessage()).orElse("Invalid value"));
            errs.add(e);
        });

        Map<String, Object> inner = new LinkedHashMap<>();
        inner.put("code", "VALIDATION");
        inner.put("details", errs);

        Map<String, Object> body = new HashMap<>();
        body.put("error", inner);

        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> onOther(Exception ex) {
        Map<String, Object> inner = new LinkedHashMap<>();
        inner.put("code", "INTERNAL");
        inner.put("message", "Unexpected error");

        Map<String, Object> body = new HashMap<>();
        body.put("error", inner);

        return ResponseEntity.status(500).body(body);
    }
}