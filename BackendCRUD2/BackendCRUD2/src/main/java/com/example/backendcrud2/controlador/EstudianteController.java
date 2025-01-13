package com.example.backendcrud2.controlador;

import com.example.backendcrud2.modelo.Estudiante;
import com.example.backendcrud2.modelo.Materia;
import com.example.backendcrud2.servicio.EstudianteService;
import com.example.backendcrud2.exception.ResourceNotFoundException;
import com.example.backendcrud2.exception.DuplicateResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    @Autowired
    private EstudianteService estudianteService;

    @GetMapping("/obtenerPorMatricula")
    public ResponseEntity<Estudiante> obtenerEstudiantePorMatricula(@RequestParam("matricula") String matricula) {
        try {
            Estudiante estudiante = estudianteService.obtenerEstudiantePorMatricula(matricula);
            return ResponseEntity.ok(estudiante);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/comprobar")
    public ResponseEntity<Boolean> comprobarEstudiante(@RequestParam("boleta") String boleta, @RequestParam("correo") String correo) {
        try {
            Estudiante estudiante = estudianteService.obtenerEstudiantePorBoletaYCorreo(boleta, correo);
            return ResponseEntity.ok(estudiante != null);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.ok(false);
        }
    }

    @PostMapping
    public ResponseEntity<Estudiante> crearEstudiante(@RequestBody Estudiante estudiante) {
        try {
            Estudiante nuevoEstudiante = estudianteService.crearEstudiante(estudiante);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEstudiante);
        } catch (DuplicateResourceException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> obtenerEstudiante(@PathVariable Long id) {
        try {
            Estudiante estudiante = estudianteService.obtenerEstudiante(id);
            return ResponseEntity.ok(estudiante);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{estudianteId}/materias")
    public ResponseEntity<List<Materia>> obtenerMateriasInscritas(@PathVariable Long estudianteId) {
        try {
            List<Materia> materias = estudianteService.obtenerMateriasInscritas(estudianteId);
            return ResponseEntity.ok(materias);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/{estudianteId}/materias/{materiaId}")
    public ResponseEntity<Void> inscribirMateria(@PathVariable Long estudianteId, @PathVariable Long materiaId) {
        try {
            estudianteService.inscribirMateria(estudianteId, materiaId);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{estudianteId}/materias/{materiaId}")
    public ResponseEntity<Void> eliminarMateria(@PathVariable Long estudianteId, @PathVariable Long materiaId) {
        try {
            estudianteService.eliminarMateria(estudianteId, materiaId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // EstudianteController.java
    @GetMapping("/materiasPorMatricula")
    public ResponseEntity<List<Materia>> obtenerMateriasInscritasPorMatricula(@RequestParam("matricula") String matricula) {
        try {
            List<Materia> materias = estudianteService.obtenerMateriasInscritasPorMatricula(matricula);
            return ResponseEntity.ok(materias);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}