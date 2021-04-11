package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

@Service
public class ManejadorDeArchivosCSV {

    private String ubicacionArchivo;
    private String nombreDeArchivo;
    private char separador;

    public ManejadorDeArchivosCSV() {
        this.ubicacionArchivo = "src/main/resources/files/";
        this.nombreDeArchivo = "casosDeDengueYZika.csv";
        this.separador = ',';
    }

    public CSVParser formatearSeparador(){
        CSVParserBuilder puntoYcomaBuilder = new CSVParserBuilder();
        puntoYcomaBuilder = puntoYcomaBuilder.withSeparator(separador);
        CSVParser parser = puntoYcomaBuilder.build();
        return parser;
    }

    public FileReader obtenerArchivo(){
        FileReader reader = null;
        try {
            reader = new FileReader(ubicacionArchivo + nombreDeArchivo);
        } catch (FileNotFoundException e) {
            String mensaje = "Archivo no encontrado, verifique el nombre y/o la ubicación del mismo";
            System.out.println(mensaje);
            e.printStackTrace();
        }
        return reader;
    }

    public CSVReader obtenerArchivoFormateado(){
        FileReader fileReader = this.obtenerArchivo();
        CSVReaderBuilder readerBuilder = new CSVReaderBuilder(fileReader);
        CSVParser separadorFormateado = this.formatearSeparador();
        readerBuilder = readerBuilder.withCSVParser(separadorFormateado);
        CSVReader reader = readerBuilder.build();
        return reader;
    }

    public Iterator<String[]> obtenerIterador(){
        CSVReader reader = this.obtenerArchivoFormateado();
        Iterator<String[]> iterador = reader.iterator();
        return iterador;
    }

    public void imprimirDatosConEspacios() throws FileNotFoundException {

        CSVReader reader = this.obtenerArchivoFormateado();
        Iterator<String[]> iterador = reader.iterator();

        while (iterador.hasNext()){
            String[] fila = iterador.next();
            String filaInfo = fila[0] + " " + fila[1] + " " + fila[2] + " " + fila[3] + " " + fila[4] + " " + fila[5] + " " + fila[6] + " " + fila[7] + " " + fila[8] + " " + fila[9];
            System.out.println(filaInfo);

        }
    }

    public HashMap<String,Object> obtenerHashMapConInfo(){

        CSVReader reader = this.obtenerArchivoFormateado();
        Iterator<String[]> iterador = reader.iterator();
        HashMap<String,Object> informacion = new HashMap<>();
        ArrayList<CasoConfirmado> casosConfirmados = new ArrayList<>();

        while (iterador.hasNext()){
            String[] fila = iterador.next();

            String idDepartamento = fila[0];
            String nombreDepartamento = fila[1];
            int idProvincia  = Integer.parseInt(fila[2]);
            String nombreProvincia = fila[3];
            int anio = Integer.parseInt(fila[4]);
            int semanas = Integer.parseInt(fila[5]);
            String evento = fila[6];
            int idGrupoEdad = 0;
            if (!fila[7].equals("")){
                idGrupoEdad = Integer.parseInt(fila[7]);
            }
            String grupoEtario = fila[8];
            int cantidadDeCasos = Integer.parseInt(fila[9]);
            CasoConfirmado caso = new CasoConfirmado(idDepartamento,nombreDepartamento,idProvincia,nombreProvincia,anio,semanas,evento,idGrupoEdad,grupoEtario,cantidadDeCasos);
            casosConfirmados.add(caso);
        }

        informacion.put("casos",casosConfirmados);

        return informacion;

    }

    public static void main(String[] args){
        ManejadorDeArchivosCSV m = new ManejadorDeArchivosCSV();


    }

}
