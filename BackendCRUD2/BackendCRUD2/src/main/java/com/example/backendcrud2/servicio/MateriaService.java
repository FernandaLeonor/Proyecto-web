package com.example.backendcrud2.servicio;

import com.example.backendcrud2.modelo.Materia;
import com.example.backendcrud2.modelo.Profesor;
import com.example.backendcrud2.repositorio.MateriaRepository;
import com.example.backendcrud2.exception.ResourceNotFoundException;
import com.example.backendcrud2.exception.DuplicateResourceException;
import com.example.backendcrud2.repositorio.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MateriaService {

    @Autowired
    private MateriaRepository materiaRepository;
    @Autowired
    private ProfesorRepository profesorRepository;

    public List<Materia> obtenerMateriasPorProfesor(Long id) {
        return materiaRepository.findByProfesorId(id);
    }

    public Materia crearMateria(Materia materia, Long profesorId) {
        if (materiaRepository.existsByClave(materia.getClave())) {
            throw new DuplicateResourceException("La clave ya está en uso.");
        }

        if (profesorId != null) {
            Profesor profesor = profesorRepository.findById(profesorId)
                    .orElseThrow(() -> new ResourceNotFoundException("Profesor no encontrado"));
            materia.setProfesor(profesor);
        } else {
            throw new ResourceNotFoundException("Se requiere el ID del profesor");
        }

        return materiaRepository.save(materia);
    }

    public Materia obtenerMateria(Long id) {
        return materiaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Materia no encontrada"));
    }

    public List<Materia> obtenerTodasMaterias() {
        return materiaRepository.findAll();
    }

    public Materia actualizarMateria(Long id, Materia materiaDetalles, Long profesorId) {
        Materia materia = materiaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Materia no encontrada"));

        materia.setClave(materiaDetalles.getClave());
        materia.setNombre(materiaDetalles.getNombre());
        materia.setDescripcion(materiaDetalles.getDescripcion());
        materia.setDuracion(materiaDetalles.getDuracion());
        materia.setCarrera(materiaDetalles.getCarrera());

        if (profesorId != null) {
            Profesor profesor = profesorRepository.findById(profesorId)
                    .orElseThrow(() -> new ResourceNotFoundException("Profesor no encontrado"));
            materia.setProfesor(profesor);
        } else if (materiaDetalles.getProfesor() != null) {
            materia.setProfesor(materiaDetalles.getProfesor());
        } else {
            // Si no se proporciona el profesor, mantén el existente
            // materia.setProfesor(materia.getProfesor());
        }

        return materiaRepository.save(materia);
    }

    public void eliminarMateria(Long id) {
        Materia materia = materiaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Materia no encontrada"));
        materiaRepository.delete(materia);
    }
}
