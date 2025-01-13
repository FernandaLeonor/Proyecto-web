package com.example.backendcrud2.controlador;

import com.example.backendcrud2.modelo.Materia;
import com.example.backendcrud2.servicio.MateriaService;
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
@RequestMapping("/api/materias")
public class MateriaController {

    @Autowired
    private MateriaService materiaService;

    @GetMapping("/obtenerPorProfesor")
    public ResponseEntity<List<Materia>> obtenerMateriasPorProfesor(@RequestParam("id") Long id) {
        List<Materia> materias = materiaService.obtenerMateriasPorProfesor(id);
        return ResponseEntity.ok(materias);
    }

    @PostMapping
    public ResponseEntity<Materia> crearMateria(@RequestBody Materia materia, @RequestParam(required = false) Long profesorId) {
        try {
            Materia nuevaMateria = materiaService.crearMateria(materia, profesorId);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaMateria);
        } catch (DuplicateResourceException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Materia> obtenerMateria(@PathVariable Long id) {
        try {
            Materia materia = materiaService.obtenerMateria(id);
            return ResponseEntity.ok(materia);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Materia>> obtenerTodasMaterias() {
        List<Materia> materias = materiaService.obtenerTodasMaterias();
        return ResponseEntity.ok(materias);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Materia> actualizarMateria(@PathVariable Long id, @RequestBody Materia materiaDetalles, @RequestParam(required = false) Long profesorId) {
        try {
            Materia materiaActualizada = materiaService.actualizarMateria(id, materiaDetalles, profesorId);
            return ResponseEntity.ok(materiaActualizada);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMateria(@PathVariable Long id) {
        try {
            materiaService.eliminarMateria(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
