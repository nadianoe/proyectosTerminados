package com.company;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

public class Main {

    public static void main(String[] args) {

        //Creating a MongoDB client
        MongoClient mongo = new MongoClient( "localhost" , 27017 );
        //Connecting to the database
        MongoDatabase database = mongo.getDatabase("icosos");
        //Creating a collection

        //warninggggg
        //database.createCollection("cosas");

        //Preparing a document

        /*
        List<Document> docs = new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            Document document = new Document();
            document.append("num",i);
            document.append("data",i);
            docs.add(document);
        }

         */

        /*
        Document document = new Document();
        document.append("name", "Ram");
        document.append("age", 26);
        document.append("city", "Hyderabad");
        Document document1 = new Document();
        document1.append("lala",23);
        document1.append("lolo",2323);

        document.append("doc1",document1);

        */

        //Inserting the document into the collection
        //database.getCollection("cosas").insertMany(docs);
        System.out.println("Document inserted successfully");

        MongoCollection collection = database.getCollection("cosas");

        System.out.println(collection.countDocuments());

        Document filter = new Document();
        filter.append("num",2);
        filter.append("data",4);

        //FindIterable result = collection.find(or(eq("num",2),eq("data",4)));
        FindIterable result = collection.find(or);


        System.out.println(collection.countDocuments());

        MongoCursor it = result.iterator();


int a = 0;

        while (it.hasNext()){
            Document doc = (Document) it.next();
            System.out.println("num?" + doc.get("num"));
            System.out.println(doc.toJson());
            a++;
        }

        System.out.println("total: " + a);


/*
        Mongo mongo = new Mongo("localhost", 27017);
        DB db = mongo.getDB("personitas");

        DBCollection collection = db.getCollection("profesores");

        BasicDBObject document = new BasicDBObject();
        document.put("director", "elSeñorToriggia");
        document.put("ubicacion", "Villa Pueyrredon");

        BasicDBObject documentDetail = new BasicDBObject();
        documentDetail.append()
        documentDetail.put("profesDeCompu", 23);
        documentDetail.put("profesDeMecanica", 23);
        documentDetail.put("cantidadDeLabos", 4);
        document.put("detallesDeCursos", documentDetail);

        collection.insert(document);

        BasicDBObject objetoPersona = new BasicDBObject();
        BasicDBObject dbPersonas = new BasicDBObject();

        ArrayList<Persona> personas = new ArrayList<>();
        personas.add(new Persona("nadia",23));
        personas.add(new Persona("lol",232));

        for (Persona persona : personas) {
            objetoPersona.put("nombre", persona.getNombre());
            objetoPersona.put("edad", persona.getEdad());
        }
        dbPersonas.put("listado",personas);

        collection.insert(dbPersonas);

*/


    }
}
