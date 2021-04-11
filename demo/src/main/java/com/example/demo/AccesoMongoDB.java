package com.example.demo;

import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.util.JSON;
import org.bson.Document;
import org.bson.conversions.Bson;
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
        this.conectarAColeccion("packs");
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

    public HashMap<String, Object> obtenerPaginas(String pack) {
        Document filtro = new Document("nombre",pack);
        FindIterable resultado = coleccion.find(filtro);
        MongoCursor iterador = resultado.iterator();
        Document documento = (Document) iterador.next();
        ArrayList<Pagina> paginasGuardadas = (ArrayList<Pagina>) documento.get("paginas");
        HashMap<String,Object> infoSolicitada = new HashMap<>();
        System.out.println(paginasGuardadas);
        infoSolicitada.put("paginas",paginasGuardadas);
        return infoSolicitada;
    }

    public HashMap<String, Object> obtenerNombresDePacks() {

        FindIterable resultado = coleccion.find();
        MongoCursor iterador = resultado.iterator();
        ArrayList<String> nombresDePacks = new ArrayList<>();

        while (iterador.hasNext()){
            Document documento = (Document) iterador.next();
            String nombre = documento.getString("nombre");
            nombresDePacks.add(nombre);
        }

        HashMap<String,Object> infoSolicitada = new HashMap<>();
        infoSolicitada.put("nombresDePacks",nombresDePacks);
        return infoSolicitada;
    }

    public void agregarPagina(Pagina nuevaPagina) {
        Document nuevoDocumento = new Document();
        nuevoDocumento.put("nombre",nuevaPagina.getNombre());
        nuevoDocumento.put("link",nuevaPagina.getLink());
        coleccion.insertOne(nuevoDocumento);
    }

    public void agregarPack(Pack nuevoPack) {
        Document nuevoDocumento = new Document();
        nuevoDocumento.put("nombre", nuevoPack.getNombre());
        nuevoDocumento.put("paginas", nuevoPack.getPaginas());
        coleccion.insertOne(nuevoDocumento);
    }

    public void modificarPack(String nombrePack, Pagina pagina) {
        Bson filtro = Filters.eq("nombre",nombrePack);
        String nombre = pagina.getNombre();
        String link = pagina.getLink();
        String json = "{ $push :{ paginas :{nombre : \" " + nombre + " \"," + "link : \" " + link + " \" } } }";
        Bson push = (Bson) JSON.parse(json);
        coleccion.updateOne(filtro,push);
    }
}