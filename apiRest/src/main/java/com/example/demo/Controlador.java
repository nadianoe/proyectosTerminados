package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

@RequestMapping("/api")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class Controlador {

    @Autowired
    private ManejadorDeArchivosCSV archivoCSV;

    public Controlador() {
        this.archivoCSV = new ManejadorDeArchivosCSV();
    }

    @GetMapping("/")
    public String index() {
        return "inicio";
    }

    @RequestMapping(value = "/datos/csv",method = RequestMethod.GET)
    public ResponseEntity<Object> enviarDatosCSV(){
        HashMap<String,Object> datos = archivoCSV.obtenerHashMapConInfo();
        return new ResponseEntity<>(datos,HttpStatus.OK);
    }

    @RequestMapping(value = "/datos/json",method = RequestMethod.GET)
    public ResponseEntity<Object> enviarDatosJson(){
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String,Object> datos = new HashMap<>();
        ArrayList jsonDeserializado = new ArrayList();
        try {
            jsonDeserializado = mapper.readValue(new File("src/main/resources/files/vigilancia-de-dengue-y-zika.json"), ArrayList.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        datos.put("casos",jsonDeserializado);
        return new ResponseEntity<>(datos,HttpStatus.OK);
    }

}

