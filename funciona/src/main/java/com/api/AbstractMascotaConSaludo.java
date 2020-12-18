package com.api;

import org.bson.Document;

public abstract class AbstractMascotaConSaludo extends AbstractMascota {

    private int alegria;

    public AbstractMascotaConSaludo(String nombre, String dueño) {
        super(nombre, dueño);
        this.alegria = 1;
    }

    public AbstractMascotaConSaludo(String nombre, String dueño, int alegria) {
        super(nombre, dueño);
        this.alegria = alegria;
    }

    public abstract String Saludar(Boolean esElDueño);

    public int getAlegria() {
        return alegria;
    }

    public void setAlegria(int alegria) {
        this.alegria = alegria;
    }
}
