package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class Controller {

    @Autowired
    private AccesoMongoDB accesoABaseDeDatos;

    public Controller() {
        this.accesoABaseDeDatos = new AccesoMongoDB();
    }

    @RequestMapping(value = "/datos/paginas", method = RequestMethod.GET)
    public ResponseEntity<Object> obtenerPaginas() {
        HashMap<String, Object> datos = accesoABaseDeDatos.obtenerPaginas();
        return new ResponseEntity<>(datos, HttpStatus.OK);
    }

    @RequestMapping(value = "/datos/paginas", method = RequestMethod.PUT)
    public ResponseEntity<Object> agregarPagina(@RequestBody HashMap pagina) {
        String nombre = (String) pagina.get("nombre");
        String link = (String) pagina.get("link");
        Pagina nuevaPagina = new Pagina(nombre, link);
        accesoABaseDeDatos.agregarPagina(nuevaPagina);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}