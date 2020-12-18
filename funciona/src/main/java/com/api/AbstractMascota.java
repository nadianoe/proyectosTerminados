package com.api;

import org.bson.Document;

public abstract class AbstractMascota implements InterfaceMascota {

    private String nombre;
    private String dueño;
    private String tipo;

    public AbstractMascota(String nombre, String dueño) {
        this.nombre = nombre;
        this.dueño = dueño;
    }

    public abstract String Saludar(Boolean esElDueño);

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDueño() {
        return dueño;
    }

    public void setDueño(String dueño) {
        this.dueño = dueño;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
