package com.example.demo;

import java.util.ArrayList;

public class Pack {

    private String nombre;
    private ArrayList<Pagina> paginas;

    public Pack(String nombre) {
        this.nombre = nombre;
        this.paginas = new ArrayList<>();
    }

    public Pack(String nombre, ArrayList<Pagina> paginas) {
        this.nombre = nombre;
        this.paginas = paginas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Pagina> getPaginas() {
        return paginas;
    }

    public void setPaginas(ArrayList<Pagina> paginas) {
        this.paginas = paginas;
    }
}
