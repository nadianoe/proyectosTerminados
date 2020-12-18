package com.api;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class AccesoMongoDB {
    private MongoDatabase baseDeDatos;
    private MongoCollection coleccion;
    private String host;
    private int puerto;


    public AccesoMongoDB(){
        this.puerto = 27017;
        this.host = "localhost";
    }

    public void conectarBD() {
        MongoClient mongo = new MongoClient(host, puerto);
        this.baseDeDatos = mongo.getDatabase("Mascota");
    }

    public void conectarAColeccion(String nombreColeccion) {
        this.coleccion = baseDeDatos.getCollection(nombreColeccion);
    }

    public Document returnMascotaPorNombre(String nombre){
        this.conectarBD();
        this.conectarAColeccion("mascota");
        Document documento = (Document) this.coleccion.find(Filters.eq("nombre", nombre)).first();
        return documento;
    }

    public void restarVidaPez(String nombre, int vidas){
        this.conectarBD();
        this.conectarAColeccion("mascota");
        this.coleccion.updateOne(Filters.eq("nombre", nombre), Updates.set("vidas", (vidas - 1)));
    }

    public void eliminarMascota(String nombre){
        this.conectarBD();
        this.conectarAColeccion("mascota");
        this.coleccion.deleteOne(Filters.eq("nombre", nombre));
    }

    public String sumarVidas(String nombre, int vidas){
        this.conectarBD();
        this.conectarAColeccion("mascota");
        this.coleccion.updateOne(Filters.eq("nombre", nombre), Updates.set("vidas", (vidas + 1)));
        return "Vidas: " + (vidas + 1);
    }

    public String sumarAlegria(String nombre, int alegria){
        this.conectarBD();
        this.conectarAColeccion("mascota");
        this.coleccion.updateOne(Filters.eq("nombre", nombre), Updates.set("alegria", (alegria + 1)));
        return "Puntos de alegria: " + (alegria + 1);
    }

    public String insertarMascota(HashMap<String, String> mascota){
        this.conectarBD();
        this.conectarAColeccion("mascota");
        String nombre = mascota.get("nombre");
        String dueño = mascota.get("dueño");
        String tipo = mascota.get("tipo");
        Document documento = (Document) this.coleccion.find(Filters.eq("nombre", nombre)).first();
        if (documento != null){
            return "Ya existe una mascota con ese nombre";
        }
        Document mascotaAInsertar = new Document();
        mascotaAInsertar.append("nombre", nombre);
        mascotaAInsertar.append("dueño", dueño);
        mascotaAInsertar.append("tipo", tipo);
        if (tipo.equals("Pez")){
            mascotaAInsertar.append("vidas", 10);
            this.coleccion.insertOne(mascotaAInsertar);
            return "Se registro correctamete";
        }
        else {
            mascotaAInsertar.append("alegria", 1);
            this.coleccion.insertOne(mascotaAInsertar);
            return "Se registro correctamete";
        }

    }

    public List<HashMap> mostrarMascotas(){
        this.conectarBD();
        this.conectarAColeccion("mascota");
        List<HashMap> listaMascotas = new ArrayList<>();
        FindIterable resultados = coleccion.find();
        MongoCursor iterador = resultados.iterator();
        while(iterador.hasNext()) {
            Document documento = (Document) iterador.next();
            HashMap datosMascota = new HashMap();
            datosMascota.put("nombre", documento.getString("nombre"));
            datosMascota.put("dueño", documento.getString("dueño"));
            datosMascota.put("tipo", documento.getString("tipo"));
            if (documento.getString("tipo").equals("Pez")){
                datosMascota.put("vidas", documento.getInteger("vidas"));
            }
            else {
                datosMascota.put("alegria", documento.getInteger("alegria"));
            }
            listaMascotas.add(datosMascota);
        }
        return listaMascotas;
    }

    public MongoDatabase getBaseDeDatos() {
        return baseDeDatos;
    }

    public void setBaseDeDatos(MongoDatabase baseDeDatos) {
        this.baseDeDatos = baseDeDatos;
    }

    public MongoCollection getColeccion() {
        return coleccion;
    }

    public void setColeccion(MongoCollection coleccion) {
        this.coleccion = coleccion;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }
}
