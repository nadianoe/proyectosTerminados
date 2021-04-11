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

    @RequestMapping(value = "/datos/{pack}/paginas", method = RequestMethod.GET)
    public ResponseEntity<Object> obtenerPaginas(@PathVariable String pack) {
        HashMap<String, Object> datos = accesoABaseDeDatos.obtenerPaginas(pack);
        return new ResponseEntity<>(datos, HttpStatus.OK);
    }

    @RequestMapping(value = "/datos/packs", method = RequestMethod.GET)
    public ResponseEntity<Object> obtenerNombresDePacks() {
        HashMap<String, Object> datos = accesoABaseDeDatos.obtenerNombresDePacks();
        return new ResponseEntity<>(datos, HttpStatus.OK);
    }

    @RequestMapping(value = "/datos/packs", method = RequestMethod.PUT)
    public ResponseEntity<Object> agregarNuevoPack(@RequestBody HashMap pack) {
        String nombre = (String) pack.get("nombre");
        Pack nuevoPack = new Pack(nombre);
        accesoABaseDeDatos.agregarPack(nuevoPack);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/datos/{nombreDePack}/paginas", method = RequestMethod.PUT)
    public ResponseEntity<Object> agregarPagina(@PathVariable String nombreDePack, @RequestBody HashMap pagina) {
        String nombre = (String) pagina.get("nombre");
        String link = (String) pagina.get("link");
        Pagina nuevaPagina = new Pagina(nombre, link);
        accesoABaseDeDatos.modificarPack(nombreDePack,nuevaPagina);
        return new ResponseEntity<>(HttpStatus.OK);
    }





}