package com.example.backendcrud2.servicio;

import com.example.backendcrud2.modelo.Estudiante;
import com.example.backendcrud2.modelo.Materia;
import com.example.backendcrud2.repositorio.EstudianteRepository;
import com.example.backendcrud2.exception.ResourceNotFoundException;
import com.example.backendcrud2.exception.DuplicateResourceException;
import com.example.backendcrud2.repositorio.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    public Estudiante obtenerEstudiantePorMatricula(String matricula) {
        return estudianteRepository.findByMatricula(matricula)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado"));
    }

    public Estudiante obtenerEstudiantePorBoletaYCorreo(String boleta, String correo) {
        return estudianteRepository.findByBoletaAndCorreo(boleta, correo)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado"));
    }

    public List<Estudiante> obtenerTodosLosEstudiantes() {
        return estudianteRepository.findAll();
    }

    public Estudiante crearEstudiante(Estudiante estudiante) {
        if (estudianteRepository.existsByCorreo(estudiante.getCorreo())) {
            throw new DuplicateResourceException("El correo ya está en uso.");
        }
        return estudianteRepository.save(estudiante);
    }

    public Estudiante obtenerEstudiante(Long id) {
        return estudianteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado"));
    }

    public List<Materia> obtenerMateriasInscritas(Long estudianteId) {
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado"));
        return estudiante.getMateriasInscritas();
    }

    public void inscribirMateria(Long estudianteId, Long materiaId) {
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado"));
        Materia materia = new Materia();
        materia.setId(materiaId);
        estudiante.agregarMateria(materia);
        estudianteRepository.save(estudiante);
    }

    public void eliminarMateria(Long estudianteId, Long materiaId) {
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado"));
        Materia materia = materiaRepository.findById(materiaId)
                .orElseThrow(() -> new ResourceNotFoundException("Materia no encontrada"));

        // Elimina la materia del estudiante
        estudiante.eliminarMateria(materia);
        estudianteRepository.save(estudiante);
    }

    // EstudianteService.java
    public List<Materia> obtenerMateriasInscritasPorMatricula(String matricula) {
        Estudiante estudiante = estudianteRepository.findByMatricula(matricula)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado"));
        return estudiante.getMateriasInscritas();
    }
}