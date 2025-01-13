package com.example.backendcrud2.controlador;

import com.example.backendcrud2.modelo.Estudiante;
import com.example.backendcrud2.modelo.Profesor;
import com.example.backendcrud2.servicio.ProfesorService;
import com.example.backendcrud2.exception.ResourceNotFoundException;
import com.example.backendcrud2.exception.DuplicateResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profesores")
public class ProfesorController {

    @Autowired
    private ProfesorService profesorService;

    @GetMapping("/obtenerPorRfcYCorreo")
    public ResponseEntity<Profesor> obtenerProfesorPorRfcYCorreo(@RequestParam("rfc") String rfc, @RequestParam("correo") String correo) {
        try {
            Profesor profesor = profesorService.obtenerProfesorPorRfcYCorreo(rfc, correo);
            return ResponseEntity.ok(profesor);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/comprobar")
    public ResponseEntity<Boolean> comprobarProfesor(@RequestParam("rfc") String rfc, @RequestParam("correo") String correo) {
        try {
            Profesor profesor = profesorService.obtenerProfesorPorRfcYCorreo(rfc, correo);
            return ResponseEntity.ok(profesor != null);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.ok(false);
        }
    }

    @PostMapping
    public ResponseEntity<Profesor> crearProfesor(@RequestBody Profesor profesor) {
        try {
            Profesor nuevoProfesor = profesorService.crearProfesor(profesor);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProfesor);
        } catch (DuplicateResourceException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profesor> obtenerProfesor(@PathVariable Long id) {
        try {
            Profesor profesor = profesorService.obtenerProfesor(id);
            return ResponseEntity.ok(profesor);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Profesor>> obtenerTodosProfesores() {
        List<Profesor> profesores = profesorService.obtenerTodosProfesores();
        return ResponseEntity.ok(profesores);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Profesor> actualizarProfesor(@PathVariable Long id, @RequestBody Profesor profesorDetalles) {
        try {
            Profesor profesorActualizado = profesorService.actualizarProfesor(id, profesorDetalles);
            return ResponseEntity.ok(profesorActualizado);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProfesor(@PathVariable Long id) {
        try {
            profesorService.eliminarProfesor(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}