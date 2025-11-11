package com.jdk21.academia.features.comunidad.config;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 409 - Duplicados / claves únicas (p.ej. codigo)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ProblemDetail> handleDataIntegrity(
            DataIntegrityViolationException ex, HttpServletRequest req) {

        String detail = "Conflicto de datos.";
        String most = Optional.ofNullable(ex.getMostSpecificCause())
                .map(Throwable::getMessage).orElse("");

        // Personaliza por constraint
        if (most.contains("comunidad_codigo_key")) {
            detail = "No se pueden crear dos comunidades con el mismo código.";
        }
        // Puedes añadir más constraints específicos aquí…

        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        pd.setTitle("Conflicto");
        pd.setDetail(detail);
        pd.setProperty("path", req.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(pd);
    }

    // 400 - JSON mal formado o tipos inválidos
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ProblemDetail> handleNotReadable(
            HttpMessageNotReadableException ex, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setTitle("Solicitud inválida");
        pd.setDetail("Cuerpo de la petición inválido o con formato incorrecto.");
        pd.setProperty("path", req.getRequestURI());
        return ResponseEntity.badRequest().body(pd);
    }

    // 400 - Validaciones @Valid fallidas en DTO (body)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleMethodArgNotValid(
            MethodArgumentNotValidException ex, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setTitle("Datos no válidos");

        Map<String, String> errors = new LinkedHashMap<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            errors.put(fe.getField(), fe.getDefaultMessage());
        }
        pd.setDetail("Hay campos con errores.");
        pd.setProperty("errors", errors);
        pd.setProperty("path", req.getRequestURI());
        return ResponseEntity.badRequest().body(pd);
    }

    // 400 - Validaciones @Validated en parámetros (query/path)
    @ExceptionHandler({ConstraintViolationException.class, TransactionSystemException.class})
    public ResponseEntity<ProblemDetail> handleConstraintViolation(
            Exception ex, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setTitle("Validación");
        pd.setDetail("Parámetros de la petición no cumplen las restricciones.");
        pd.setProperty("path", req.getRequestURI());
        return ResponseEntity.badRequest().body(pd);
    }

    // 404 - No encontrado
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleNotFound(
            EntityNotFoundException ex, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        pd.setTitle("No encontrado");
        pd.setDetail(ex.getMessage() != null ? ex.getMessage() : "Recurso no encontrado.");
        pd.setProperty("path", req.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pd);
    }

    // 405 - Método HTTP no soportado
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ProblemDetail> handleMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.METHOD_NOT_ALLOWED);
        pd.setTitle("Método no permitido");
        pd.setDetail("El método HTTP no está permitido para esta ruta.");
        pd.setProperty("path", req.getRequestURI());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(pd);
    }

    // 415 - Media type no soportado
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ProblemDetail> handleMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        pd.setTitle("Tipo de contenido no soportado");
        pd.setDetail("Usa 'application/json' en Content-Type.");
        pd.setProperty("path", req.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(pd);
    }

    // 500 - Fallback: mensaje corto sin stacktrace
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGeneric(
            Exception ex, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        pd.setTitle("Error interno");
        pd.setDetail("Ha ocurrido un error inesperado.");
        pd.setProperty("path", req.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(pd);
    }
}

