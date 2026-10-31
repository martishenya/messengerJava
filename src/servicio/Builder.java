package servicio;

import modelos.Chat;
import modelos.Usuario;

public class Builder {


    public static void datosEjemplo(GestorMensajeria mensajeria){

        mensajeria.crearUsuario("Arjun","Penkar", "A", "1234");
        mensajeria.crearUsuario("Carla", "Zhu", "carlaaa", "1234");
        mensajeria.crearUsuario("Maxim", "Martishenya", "makson", "1234");

        Usuario us1 = mensajeria.buscarUsuario("A");
        Usuario us2 = mensajeria.buscarUsuario("carlaaa");
        Usuario us3 = mensajeria.buscarUsuario("makson");

        mensajeria.crearChatPrivado(us1,us2);
        mensajeria.crearChatPrivado(us1,us3);

        Chat chat12 = mensajeria.buscarChatPrivado(us1,us2);
        Chat chat13 = mensajeria.buscarChatPrivado(us1,us3);


        mensajeria.enviarMensaje(chat12, us1, "Hola que tal?");
        mensajeria.enviarMensaje(chat12, us2, "Hola, bien y tu??");

        mensajeria.enviarMensaje(chat13, us3, "Hola, recuerdas cuando tenemos el parcial de java??");
        mensajeria.enviarMensaje(chat13, us1, "Hola, este jueves, no?");



    }
}
