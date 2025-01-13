package com.example.backendcrud2.repositorio;

import com.example.backendcrud2.modelo.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long> {

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM Materia m WHERE m.clave = :clave AND m.id != :id")
    boolean existsByClaveAndIdNot(@Param("clave") String clave, @Param("id") Long id);

    @Query("SELECT m FROM Materia m WHERE m.profesor.id = :profesorId")
    List<Materia> findByProfesorId(@Param("profesorId") Long profesorId);

    boolean existsByClave(String clave);

}