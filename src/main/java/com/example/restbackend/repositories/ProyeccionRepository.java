package com.example.restbackend.repositories;

import com.example.restbackend.model.proyeccion.Proyeccion;
import com.example.restbackend.model.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface ProyeccionRepository extends JpaRepository<Proyeccion, Long> {
    @Query("SELECT s FROM Proyeccion s WHERE s.id = ?1")
    Optional<Proyeccion> findProyeccionByID(Long id);

    @Query("SELECT s FROM Proyeccion s WHERE s.codCurso = ?1")
    Optional<Proyeccion> findProyeccionByCodCur(String codCur);

    @Query("SELECT s FROM Proyeccion s WHERE s.nomCurso = ?1")
    Optional<Proyeccion> findProyeccionByNomCur(String nomCur);

    @Query("SELECT s FROM Proyeccion s WHERE s.nomCarrera = ?1")
    Collection<Proyeccion> findProyeccionByNomCarr(String nomCarr);
}
