package com.example.backendcrud2.servicio;

import com.example.backendcrud2.modelo.Profesor;
import com.example.backendcrud2.repositorio.ProfesorRepository;
import com.example.backendcrud2.exception.ResourceNotFoundException;
import com.example.backendcrud2.exception.DuplicateResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfesorService {

    @Autowired
    private ProfesorRepository profesorRepository;

    public Profesor obtenerProfesorPorRfcYCorreo(String rfc, String correo) {
        return profesorRepository.findByRfcAndCorreo(rfc, correo)
                .orElseThrow(() -> new ResourceNotFoundException("Profesor no encontrado"));
    }

    public Profesor crearProfesor(Profesor profesor) {
        if (profesorRepository.existsByRfc(profesor.getRfc())) {
            throw new DuplicateResourceException("El RFC ya está en uso.");
        }
        return profesorRepository.save(profesor);
    }

    public Profesor obtenerProfesor(Long id) {
        return profesorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profesor no encontrado"));
    }

    public List<Profesor> obtenerTodosProfesores() {
        return profesorRepository.findAll();
    }

    public Profesor actualizarProfesor(Long id, Profesor profesorDetalles) {
        Profesor profesor = profesorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profesor no encontrado"));

        profesor.setRfc(profesorDetalles.getRfc());
        profesor.setNombre(profesorDetalles.getNombre());
        profesor.setApellido(profesorDetalles.getApellido());
        profesor.setCorreo(profesorDetalles.getCorreo());
        profesor.setMateriasAsignadas(profesorDetalles.getMateriasAsignadas());

        return profesorRepository.save(profesor);
    }

    public void eliminarProfesor(Long id) {
        Profesor profesor = profesorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profesor no encontrado"));
        profesorRepository.delete(profesor);
    }
}