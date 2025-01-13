package com.example.backendcrud2.repositorio;

import com.example.backendcrud2.modelo.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    Optional<Estudiante> findByMatricula(String matricula);
    boolean existsByMatricula(String matricula);
    boolean existsByCorreo(String correo);

    @Query("SELECT e FROM Estudiante e WHERE e.matricula = :boleta AND e.correo = :correo")
    Optional<Estudiante> findByBoletaAndCorreo(@Param("boleta") String boleta, @Param("correo") String correo);
}
