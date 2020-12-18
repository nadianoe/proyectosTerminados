package com.api;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class Controlador {

    @Autowired
    private AccesoMongoDB accesoABaseDeDatos;

    @PostMapping(value = "/saludar", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public String recibirSaludo(@RequestBody HashMap<String,String> datosIngresados){
        Document mascota = accesoABaseDeDatos.returnMascotaPorNombre(datosIngresados.get("nombre"));
        if (mascota == null){
            return "No existe una mascota con ese nombre";
        }
        String tipoMascota = mascota.getString("tipo");
        String dueñoIngresado = datosIngresados.get("dueño");
        Boolean esElDueño = false;
        if (dueñoIngresado.equals(mascota.getString("dueño"))) {
            esElDueño = true;
        }

        if (tipoMascota.equals("Perro")){
            Perro perro = new Perro(mascota.getString("nombre"), mascota.getString("dueño"), mascota.getInteger("alegria"));
            return perro.Saludar(esElDueño);
        }
        else if (tipoMascota.equals("Gato")){
            Gato gato = new Gato(mascota.getString("nombre"), mascota.getString("dueño"), mascota.getInteger("alegria"));
            return gato.Saludar(esElDueño);
        }
        else if (tipoMascota.equals("Pajaro")){
            Pajaro pajaro = new Pajaro(mascota.getString("nombre"), mascota.getString("dueño"), mascota.getInteger("alegria"));
            return pajaro.Saludar(esElDueño);
        }
        else{
            Pez pez = new Pez(mascota.getString("nombre"), mascota.getString("dueño"), mascota.getInteger("vidas"));
            return pez.Saludar(esElDueño);
        }
    }

    @PutMapping(value = "/alimentar", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public String alimentar(@RequestBody HashMap<String,String> mascota){
        String nombre = mascota.get("nombre");
        Document documento = accesoABaseDeDatos.returnMascotaPorNombre(nombre);
        if (documento == null){
            return "No existe la mascota";
        }
        String tipoMascota = documento.getString("tipo");
        if (tipoMascota.equals("Pez")){
            return accesoABaseDeDatos.sumarVidas(nombre, documento.getInteger("vidas"));
        }
        else {
            return accesoABaseDeDatos.sumarAlegria(nombre, documento.getInteger("alegria"));
        }
    }

    @PostMapping(value = "/crear", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public String crearMascota(@RequestBody HashMap<String,String> mascota){
        return accesoABaseDeDatos.insertarMascota(mascota);
    }

    @GetMapping(value = "/mascotas")
    public List<HashMap> mostrarTodasLasMascotas(){
        return accesoABaseDeDatos.mostrarMascotas();
    }

    @DeleteMapping(value = "/mascotas/{nombre}")
    public void eliminarMascota(@PathVariable String nombre){
        accesoABaseDeDatos.eliminarMascota(nombre);
    }

}
