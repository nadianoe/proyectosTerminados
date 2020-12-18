package com.api;

import org.bson.Document;

public class Pez extends AbstractMascotaSinSaludo {

    public Pez(String nombre, String dueño) {
        super(nombre, dueño);
        setTipo("Pez");
    }

    public Pez(String nombre, String dueño, int vidas){
        super(nombre, dueño, vidas);
        setTipo("Pez");
    }

    @Override
    public String Saludar(Boolean esElDueño){
        if (esElDueño && this.getVidas()>0) {
            accesoAMongoDB.restarVidaPez(this.getNombre(), this.getVidas());
            return "Se resto una vida al pez  Vidas: " + (this.getVidas() - 1);
        }
        accesoAMongoDB.eliminarMascota(this.getNombre());
        return "El pez se ha muerto";
    }


}
