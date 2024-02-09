package org.example;

import com.mongodb.client.*;
import com.mongodb.client.internal.MongoClientImpl;
import org.bson.Document;

import java.io.FileWriter;
import java.util.Date;

public class MetodosBD {


    private MongoDatabase database;
    private MongoCollection<Document> collection = null;



    public void InsertarDocumento(){


        try (MongoClient mongoclient = MongoClients.create("mongodb://localhost:27017")) {

            database = mongoclient.getDatabase("bbddPrueba");
            collection = database.getCollection("coleccionPrueba");

            System.out.println("Conexión establecida correctamente");


            // Verificar si la colección está configurada
            if (collection != null) {
                // Crear un nuevo documento con la fecha actual
                Document d = new Document("Fecha", new Date());
                // Insertar el documento en la colección
                collection.insertOne(d);
                System.out.println("Documento insertado de forma exitosa");
            } else {
                System.out.println("La colección no está configurada. La conexión a la base de datos puede haber fallado.");
            }
        } catch (Exception e) {
            // Manejar cualquier excepción que pueda ocurrir durante la inserción
            e.printStackTrace();
        }


    }

        public void visualizarDocumento() {

            try (MongoClient mongoclient = MongoClients.create("mongodb://localhost:27017")) {

                database = mongoclient.getDatabase("bbddPrueba");
                collection = database.getCollection("coleccionPrueba");

                System.out.println("Conexión establecida correctamente");

            FindIterable<Document> documents = collection.find();

            for(Document doc : documents){
                System.out.println(doc.toJson());
            }

        }catch (Exception e){
            e.getStackTrace();
        }
    }

    public void MongoDBToTextFile(){

        try (MongoClient mongoclient = MongoClients.create("mongodb://localhost:27017")) {

            database = mongoclient.getDatabase("bbddPrueba");
            collection = database.getCollection("coleccionPrueba");

            System.out.println("Conexión establecida correctamente");


            try (MongoCursor<Document> cursor = collection.find().iterator()){

                String file = "datos_exportados.txt";

                try(FileWriter writer = new FileWriter(file)){

                    while(cursor.hasNext()){

                        Document document = cursor.next();

                        writer.write(document.toJson());
                        writer.write("\n");

                        System.out.println("Datos exportados correctamente");

                    }
                }
            }
        }catch (Exception e){
            e.getStackTrace();
        }
    }

/*
    private void conexion(){

        try (MongoClient mongoclient = MongoClients.create("mongodb://localhost:27017")) {

        database = mongoclient.getDatabase("bbddPrueba");
        collection = database.getCollection("coleccionPrueba");

        System.out.println("Conexión establecida correctamente");

        }catch (Exception e){
            e.getStackTrace();
            System.out.println("Error al establecer la conexión: " + e.getMessage());
        }

    }

    public void cerrarConexion() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("Conexión cerrada correctamente");
        }
    }*/



}
