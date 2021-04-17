package com.example.demo;

public class CasoConfirmado {
    private String idDepartamento;
    private String nombreDepartamento;
    private int idProvincia;
    private String nombreProvincia;
    private int anio;
    private int semanas;
    private String evento;
    private int idGrupoEdad;
    private String grupoEtario;
    private int cantidadDeCasos;

    public CasoConfirmado(String idDepartamento, String nombreDepartamento, int idProvincia, String nombreProvincia, int anio, int semanas, String evento, int idGrupoEdad, String grupoEtario, int cantidadDeCasos) {
        this.idDepartamento = idDepartamento;
        this.nombreDepartamento = nombreDepartamento;
        this.idProvincia = idProvincia;
        this.nombreProvincia = nombreProvincia;
        this.anio = anio;
        this.semanas = semanas;
        this.evento = evento;
        this.idGrupoEdad = idGrupoEdad;
        this.grupoEtario = grupoEtario;
        this.cantidadDeCasos = cantidadDeCasos;
    }

    public String getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(String idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public int getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(int idProvincia) {
        this.idProvincia = idProvincia;
    }

    public String getNombreProvincia() {
        return nombreProvincia;
    }

    public void setNombreProvincia(String nombreProvincia) {
        this.nombreProvincia = nombreProvincia;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getSemanas() {
        return semanas;
    }

    public void setSemanas(int semanas) {
        this.semanas = semanas;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public int getIdGrupoEdad() {
        return idGrupoEdad;
    }

    public void setIdGrupoEdad(int idGrupoEdad) {
        this.idGrupoEdad = idGrupoEdad;
    }

    public String getGrupoEtario() {
        return grupoEtario;
    }

    public void setGrupoEtario(String grupoEtario) {
        this.grupoEtario = grupoEtario;
    }

    public int getCantidadDeCasos() {
        return cantidadDeCasos;
    }

    public void setCantidadDeCasos(int cantidadDeCasos) {
        this.cantidadDeCasos = cantidadDeCasos;
    }
}
