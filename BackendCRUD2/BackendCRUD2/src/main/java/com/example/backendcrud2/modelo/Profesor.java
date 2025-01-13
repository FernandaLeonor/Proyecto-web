package com.example.backendcrud2.modelo;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "profesores")
public class Profesor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String rfc;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false, unique = true)
    private String correo;

    // Relación con Materias
    @OneToMany(mappedBy = "profesor")
    private List<Materia> materiasAsignadas = new ArrayList<>();

    public Profesor() {}

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public List<Materia> getMateriasAsignadas() {
        return materiasAsignadas;
    }

    public void setMateriasAsignadas(List<Materia> materiasAsignadas) {
        this.materiasAsignadas = materiasAsignadas;
    }

    public void agregarMateria(Materia materia) {
        this.materiasAsignadas.add(materia);
        materia.setProfesor(this);
    }

    public void eliminarMateria(Materia materia) {
        this.materiasAsignadas.remove(materia);
        materia.setProfesor(null);
    }
}