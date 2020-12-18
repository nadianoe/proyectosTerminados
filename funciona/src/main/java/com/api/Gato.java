package com.api;

import org.bson.Document;

public class Gato extends AbstractMascotaConSaludo {

    public Gato(String nombre, String dueño) {
        super(nombre, dueño);
        setTipo("Gato");
    }

    public Gato(String nombre, String dueño, int alegria) {
        super(nombre, dueño, alegria);
        setTipo("Gato");
    }

    @Override
    public String Saludar(Boolean esElDueño){
        String saludo = "";
        if (esElDueño){
            for (int i = 1; i <= this.getAlegria(); i++) {
                saludo+="miau ";
            }
        }
        else{
            saludo = "MIAU!";
        }
        return saludo;
    }
}
