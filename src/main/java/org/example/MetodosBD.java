package org.example;

import com.mongodb.client.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class MetodosBD {


    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<Document> collection;

    //Lo primero definimos la conexion estatica para no repetir constantemente la apertura de la conexion y no saturar el sistema

    static {
        try {

                mongoClient = MongoClients.create("mongodb://localhost:27017");
                database = mongoClient.getDatabase("bbddPrueba");
                collection = database.getCollection("coleccionPrueba");
                System.out.println("Conexión establecida correctamente");

        } catch (Exception e) {
            // Manejar cualquier excepción que pueda ocurrir durante la inserción
            e.printStackTrace();
        }
    }


    //El primer metodo sirve para insertar en la base de datos contenido
    public void InsertarDocumento(){

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


    }

    //El siguiente metodo sirve para visualizar el contenido de la base de datos.

        public void visualizarDocumento() {

        //Con este objeto definimos la colleccion con el metodo find que en mongoDB sirve para imprimir todo el contenido de dicha collecion

            FindIterable<Document> documents = collection.find();

            //Una vez el objeto documents tenga todos los datos sacaremos por pantalla con un for each todos los datos
            for(Document doc : documents){
                System.out.println(doc.toJson());
            }

    }

    //Este metodo sirve para meter el contenido de la base de datos en un txt en la ubicacion definida en formato JSON

    public void MongoDBToTextFile(){


        //Definimos un cursor el cual se hara cargo de mover todos los datos de la bbdd
            try (MongoCursor<Document> cursor = collection.find().iterator()){

                //Definimos el fichero donde deseamos contener esta base de datos
                File file = new File("datos_exportados.txt");

                //Definimos un objeto de FileWrite pare escribir en el archivo

                try(FileWriter writer = new FileWriter(file)){

                    //Y mediante un bucle while metemos todo el contemido en el fichero

                    while(cursor.hasNext()){

                        Document document = cursor.next();

                        writer.write(document.toJson());
                        writer.write("\n");

                        System.out.println("Datos exportados correctamente");

                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
    }

    //Este metodo sirve para hacer recuento de todos los registros de la bbdd
    public void contarRegidtros(){

            //Puedes agregar tus filtros aquí
            Document filtros = new Document();

            //Con esta sentencia meto en una variable todos los registros para poder imprimirlos
            long numRegistros = collection.countDocuments(filtros);
            System.out.println("Número de registros encontrados: " + numRegistros);



    }

    //Este metodo borra un registro
    public void BorrarUnRegistro(){

            //Aqui agregas el id que quieres borrar
            Document filtros = new Document("_id","65ce5d55d7b98116416ee70e");

            //Y con el metodo DeleteOne lo eliminas
            DeleteResult eliminado = collection.deleteOne(filtros);
            System.out.println("Registro eliminado: " + eliminado.getDeletedCount());

    }

    //Este metodo actualiza un registro
    public void ActualizarUnRegistro(){

            //Definimos dos variables del tipo document en el cual la variable id es la clave del registro que queremos ahcer update
            Document id = new Document("_id",new ObjectId("65ce619ee5aa6229b0410a5a"));

            //En esta variable definiremos que paremetro queremos actualizar
            Document setUpdate = new Document("$set",new Document("Fecha","3"));

            //Lo añadimos a una variable del tipo UpdateResult
            UpdateResult update = collection.updateOne(id,setUpdate);

            System.out.println("Campo actualizado correctamente: " + update.getModifiedCount());

            //Despues haremos uso del metodo creado anteriormente para visualizar de nuevo la BBDD
            visualizarDocumento();
    }

}
