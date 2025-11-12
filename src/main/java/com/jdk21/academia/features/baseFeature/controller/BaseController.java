package com.jdk21.academia.features.baseFeature.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.jdk21.academia.domain.BaseEntity;
import com.jdk21.academia.features.baseFeature.service.BaseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;


//Esta página es algo caos puesto que Spring confunde la anotacion de swagger de la normal y no hace el binding correcto
//TODO: Revisar en limpieza
public abstract class BaseController<E extends BaseEntity, I, O, ID> {

    protected final BaseService<E, I, O, ID> service;

    protected BaseController(BaseService<E, I, O, ID> service) {
        this.service = service;
    }

    // CREAR
    @PostMapping
    @Operation(
        summary = "Crea una nueva (entidad)",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "DTO para creación",
            required = true,
            content = @Content(schema = @Schema(implementation = Object.class)) // Replace Object with I in concrete controller
        )
    )
    public ResponseEntity<O> create(@RequestBody I dto) {
        O result = service.create(dto);
        return ResponseEntity.ok(result);
    }

    // UPDATE
    @PutMapping("/{id}")
    @Operation(
        summary = "Actualiza una nueva (entidad)",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "DTO para actualización",
            required = true,
            content = @Content(schema = @Schema(implementation = Object.class)) // Replace Object with I in concrete controller
        )
    )
    public ResponseEntity<O> update(@PathVariable ID id, @RequestBody I dto) {
        O result = service.update(id, dto);
        return ResponseEntity.ok(result);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @Operation(summary = "Soft delete entidad por ID")
    public ResponseEntity<Void> delete(@PathVariable ID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // GET BY ID
    @GetMapping("/{id}")
    @Operation(summary = "Encuentra una entidad por ID")
    public ResponseEntity<O> getById(@PathVariable ID id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET ALL
    @GetMapping
    @Operation(summary = "Recupera todas las entidades activas")
    public ResponseEntity<List<O>> getAll() {
        List<O> list = service.getAll();
        return ResponseEntity.ok(list);
    }
}
