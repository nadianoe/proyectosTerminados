package com.company;

import com.opencsv.*;
import java.io.*;
import java.io.IOException;
import java.util.Iterator;

class Main {
    public static void main(String[] args) {

        try {

            FileReader archCSV = new FileReader("src/com/company/socios.csv");
            /*
            CSVParser conPuntoYComa = new CSVParserBuilder().withSeparator(';').build();
            CSVReader csvReader = new CSVReaderBuilder(archCSV).withCSVParser(conPuntoYComa).build();
            */
            CSVParserBuilder puntoYcomaBuilder = new CSVParserBuilder();
            puntoYcomaBuilder = puntoYcomaBuilder.withSeparator(';');
            CSVParser parser = puntoYcomaBuilder.build();

            CSVReaderBuilder readerBuilder = new CSVReaderBuilder(archCSV);
            readerBuilder = readerBuilder.withCSVParser(parser);
            CSVReader reader = readerBuilder.build();

            System.out.println();
            Iterator<String[]> iterador = reader.iterator();

            while (iterador.hasNext()){
                String[] fila = iterador.next();
                System.out.println("size" + fila.length);
                System.out.println("nombre:" + fila[0] + fila[1]);

            }



            /*
            Iterator<String[]> iterador = csvReader.iterator();

            while (iterador.hasNext()){
                String[] fila = iterador.next();
                System.out.println(fila[0] + fila[1]);

            }

             */

        }
        catch(IOException e) {
            System.out.println(e);
        }
        catch(Exception e) {
            System.out.println(e);
        }

    }
}