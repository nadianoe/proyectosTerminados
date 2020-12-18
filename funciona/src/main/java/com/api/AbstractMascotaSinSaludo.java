package com.api;

import org.bson.Document;

public abstract class AbstractMascotaSinSaludo extends AbstractMascota {

    private int vidas;

    public AbstractMascotaSinSaludo(String nombre, String dueño) {
        super(nombre, dueño);
        this.vidas = 10;
    }

    public AbstractMascotaSinSaludo(String nombre, String dueño, int vidas) {
        super(nombre, dueño);
        this.vidas = vidas;
    }

    public abstract String Saludar(Boolean esElDueño);

    public int getVidas() {
        return vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }
}
