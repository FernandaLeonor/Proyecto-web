package com.example.backendcrud2.repositorio;

import com.example.backendcrud2.modelo.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, Long> {

    Optional<Profesor> findByRfc(String rfc);
    boolean existsByRfc(String rfc);
    boolean existsByCorreo(String correo);

    @Query("SELECT p FROM Profesor p WHERE p.rfc = :rfc AND p.correo = :correo")
    Optional<Profesor> findByRfcAndCorreo(@Param("rfc") String rfc, @Param("correo") String correo);
}
