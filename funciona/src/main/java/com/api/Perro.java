package com.api;

import org.bson.Document;

public class Perro extends AbstractMascotaConSaludo {

    public Perro(String nombre, String dueño) {
        super(nombre, dueño);
        setTipo("Perro");
    }

    public Perro(String nombre, String dueño, int alegria) {
        super(nombre, dueño, alegria);
        setTipo("Perro");
    }

    @Override
    public String Saludar(Boolean esElDueño){
        String saludo = "";
        if (esElDueño){
            for (int i = 1; i <= this.getAlegria(); i++) {
                saludo+="guau ";
            }
        }
        else{
            saludo = "GUAU!";
        }
        return saludo;
    }
}
