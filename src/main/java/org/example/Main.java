package org.example;

public class Main {

    static MetodosBD m = new MetodosBD();
    public static void main(String[] args) {

        //En el main llamaremos a todos los metodos de la clase MetodosBD agregados anteriormenete para comprobar su funcionalidad

        System.out.println("Insertar");
        m.InsertarDocumento();

        System.out.println("Visualizar el documento");
        m.visualizarDocumento();

        System.out.println("Voy a pasar los datos a un fichero");
        m.MongoDBToTextFile();

        System.out.println("Contar registros");
        m.contarRegidtros();

        System.out.println("Campo eliminado");
        m.BorrarUnRegistro();

        System.out.println("Campo actualizado");
        m.ActualizarUnRegistro();

    }
}