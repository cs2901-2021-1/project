package com.example.restbackend.model.proyeccion;

import javax.persistence.*;

@Entity
@Table
public class Proyeccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = true, unique = true)
    private String codCurso;
    @Column(nullable = true, unique = true)
    private String nomCurso;
    @Column(nullable = true)
    private String nomCarrera;
    @Column(nullable = true)
    private long numAlumn;

    public Proyeccion(){}

    public Proyeccion( String codCurso, String nomCurso, String nomCarrera, long numAlumn) {
        this.nomCarrera = nomCarrera;
        this.codCurso = codCurso;
        this.nomCurso = nomCurso;
        this.numAlumn = numAlumn;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNomCarrera() {
        return nomCarrera;
    }

    public void setNomCarrera(String nomCarrera) {
        this.nomCarrera = nomCarrera;
    }

    public String getCodCurso() {
        return codCurso;
    }

    public void setCodCurso(String codCurso) {
        this.codCurso = codCurso;
    }

    public String getNomCurso() {
        return nomCurso;
    }

    public void setNomCurso(String nomCurso) {
        this.nomCurso = nomCurso;
    }

    public long getNumAlumn() {
        return numAlumn;
    }

    public void setNumAlumn(long numAlumn) {
        this.numAlumn = numAlumn;
    }


    @Override
    public String toString() {
        return "Proyeccion : " + "id = " + id + ", nombreCarrera =  " + nomCarrera
                + ", nombreCurso = " + nomCurso;
    }
}
