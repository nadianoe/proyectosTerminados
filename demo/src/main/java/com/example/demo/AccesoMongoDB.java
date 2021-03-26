package com.example.demo;

import com.mongodb.MongoClient;
import com.mongodb.client.*;
import org.bson.Document;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class AccesoMongoDB {

    private MongoDatabase baseDeDatos;
    private MongoCollection coleccion;
    private String host;
    private int puerto;

    public AccesoMongoDB() {
        this.host = "localhost";
        this.puerto = 27017;
        this.conectarABaseDeDatos("utiles");
        this.conectarAColeccion("paginas");
    }

    public void conectarABaseDeDatos(String nombreBaseDeDatos){
        MongoClient mongo = new MongoClient( host,puerto );
        this.baseDeDatos = mongo.getDatabase(nombreBaseDeDatos);
    }

    public void conectarAColeccion(String nombreDeColeccion){
        if (existeLaColeccion(nombreDeColeccion)){
            this.coleccion = baseDeDatos.getCollection(nombreDeColeccion);
        } else {
            baseDeDatos.createCollection(nombreDeColeccion);
            this.coleccion = baseDeDatos.getCollection(nombreDeColeccion);
        }
    }

    public long obtenerCantidadDeDocumentos(){
        long cantidadDeRegistros = coleccion.count();
        return cantidadDeRegistros;
    }

    public boolean existeLaColeccion(String nombreDeColeccion){

        MongoIterable<String> nombresDeColecciones = baseDeDatos.listCollectionNames();
        boolean existe = false;

        for (String nombre : nombresDeColecciones) {
            if (nombre.equals(nombreDeColeccion)){
                existe = true;
            }
        }
        return existe;
    }

    public HashMap<String, Object> obtenerPaginas() {

        FindIterable resultado = coleccion.find();
        MongoCursor iterador = resultado.iterator();
        ArrayList<Pagina> paginasGuardadas = new ArrayList<>();

        while (iterador.hasNext()){
            Document documento = (Document) iterador.next();
            String nombre = documento.getString("nombre");
            String link = documento.getString("link");
            Pagina paginaEncontrada = new Pagina(nombre,link);
            paginasGuardadas.add(paginaEncontrada);
        }

        HashMap<String,Object> infoSolicitada = new HashMap<>();
        infoSolicitada.put("paginas",paginasGuardadas);
        return infoSolicitada;
    }

    public void agregarPagina(Pagina nuevaPagina) {
        Document nuevoDocumento = new Document();
        nuevoDocumento.put("nombre",nuevaPagina.getNombre());
        nuevoDocumento.put("link",nuevaPagina.getLink());
        coleccion.insertOne(nuevoDocumento);
    }
}