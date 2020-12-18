package com.api;

import org.bson.Document;

public class Pajaro extends AbstractMascotaConSaludo {

    public Pajaro(String nombre, String dueño) {
        super(nombre, dueño);
        setTipo("Pajaro");
    }

    public Pajaro(String nombre, String dueño, int alegria) {
        super(nombre, dueño, alegria);
        setTipo("Pajaro");
    }

    @Override
    public String Saludar(Boolean esElDueño){
        String saludo = "";
        if (esElDueño){
            for (int i = 1; i <= this.getAlegria(); i++) {
                saludo+="pio ";
            }
        }

        return saludo;
    }
}
