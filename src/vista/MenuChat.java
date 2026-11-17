package vista;

import modelos.*;
import servicio.GestorMensajeria;
import servicio.Sesion;

import java.util.ArrayList;
import java.util.Scanner;

public class MenuChat {

    private Scanner sc;
    private GestorMensajeria mensajeria;
    private Sesion sesion;

    public MenuChat(Scanner sc, GestorMensajeria mensajeria, Sesion sesion) {
        this.sc = sc;
        this.mensajeria = mensajeria;
        this.sesion = sesion;
    }

    public void crearChatPrivado(){

        int check;
        int opcUsuario;
        Usuario usuario;
        ArrayList<Usuario> listaUsuarios = mensajeria.getUsuarios();

        listaUsuarios.remove(sesion.getUsuarioActivo());

//        limpiarConsola();
        System.out.println("Elige la persona con quien quieres empezar a chatear y introduce el numero correspondiente:");

        if(listaUsuarios.isEmpty()){
            System.out.println("No hay usuarios en el sistema");
            return;
        }

        mostrarListaUsuarios(listaUsuarios);




        do{

            opcUsuario = sc.nextInt();
            sc.nextLine();

            if(opcUsuario < 1 || opcUsuario > listaUsuarios.size()){

                System.out.println("El valor introducido no es valido, elige otra vez! ");


            }else {

                usuario = listaUsuarios.get(opcUsuario - 1);
                check = mensajeria.crearChatPrivado(sesion.getUsuarioActivo(), usuario);
                break;
            }

            }while(true);


                Chat chat = mensajeria.buscarChatPrivado(sesion.getUsuarioActivo(), usuario);
                if(check == -4){
                    System.out.println("Ya tienes el chat con esta persona, te trasladamos al chat..");
//                pararProg();
                    abrirChat(chat);
                    return;
                }

                abrirChat(chat);

    }

    public void crearChatGrupo(){

        String nombre;

        int i = 1;

        int opcUsuario;

        Usuario usuarioActivo = sesion.getUsuarioActivo();

        ArrayList<Usuario> usuariosAAnadir  = new ArrayList<>();
        ArrayList<Usuario> usuariosDisponibles = mensajeria.getUsuarios();

        usuariosDisponibles.remove(usuarioActivo);

        if(usuariosDisponibles.isEmpty()){
            System.out.println("No hay usuarios disponibles para crear un grupo");
            return;
        }

        System.out.println("Introduce el nombre para el grupo nuevo:");
        nombre = sc.nextLine();



        do{

            System.out.printf("Elige la persona num: %d para anadir al grupo y introduce el numero correspondiente:%n", i);
            mostrarListaUsuarios(usuariosDisponibles);

            opcUsuario = sc.nextInt();
            sc.nextLine();

            if(opcUsuario < 1 || opcUsuario > usuariosDisponibles.size()){

                System.out.println("El valor introducido no es valido, elige otra vez! ");


            }else {
                Usuario usuario = usuariosDisponibles.remove(opcUsuario - 1);
                usuariosAAnadir.add(usuario);

                i++;

                if(usuariosDisponibles.isEmpty()){

                    System.out.println("No tienes usuarios mas para anadir, por eso se crea el grupo ya");
                    break;
                }

                System.out.println("Seguir anadiendo?[y/n] ");

                char opc = sc.next().charAt(0);
                sc.nextLine();

//              cualquier char exepto 'y' va al siguiente paso: crearGrupo ya
                if(opc != 'y'){

                    break;
                }

            }



        }while(true);

        Chat chat = mensajeria.crearChatGrupo(nombre, usuariosAAnadir, usuarioActivo);
        abrirChat(chat);
    }




    public void misChats(){

        int opc;
        Usuario usuarioActual = sesion.getUsuarioActivo();
        ArrayList<Chat> chatsUsuario = mensajeria.buscarChatsPorUsuario(usuarioActual);

        if(chatsUsuario.isEmpty()){

            System.out.println("Todavia no tienes ningun chat ");
//            pararProg();
            return;

        }

//        limpiarConsola();
        System.out.println("Elige un chat/grupo para seguir chateando: ");
        mostrarListaChats(chatsUsuario);

        do{

            opc = sc.nextInt();
            sc.nextLine();

            if(opc < 1 || opc > chatsUsuario.size()){

                System.out.println("El valor introducido no es valido, elige otra vez! ");


            }else{

                break;
            }

        }while(true);

        Chat chatActual = chatsUsuario.get(opc -1);

        abrirChat(chatActual);

        return;
    }

    private void abrirChat(Chat chat){

        int opc;

        while(true){
//            limpiarConsola();
            mostrarListaMensajes(chat);

            System.out.println("Elige la accion:");
            System.out.println("1. Enviar mensaje");
            System.out.println("0. Volver al menu principal");
//        luego aqui anadimos editar/borrar/etc..

            opc = sc.nextInt();
            sc.nextLine();

            switch(opc){

                case 1:
//                    limpiarConsola();
                    mostrarListaMensajes(chat);
                    enviarMensaje(chat);
                    break;
                case 0:

                    return;
                default:
                    System.out.println("El valor introducido no es valido, prueba otra vez!");



//                    pararProg();

                    break;

            }
        }
    }

    private void enviarMensaje(Chat chat){

        Usuario usuarioActivo = sesion.getUsuarioActivo();

        String textMensaje;
        System.out.println("Escribe tu nuevo mensaje:");

        textMensaje = sc.nextLine();

        mensajeria.enviarMensaje(chat, usuarioActivo, textMensaje);
    }

    private void mostrarListaMensajes(Chat chat){

        Usuario usuarioActivo = sesion.getUsuarioActivo();
        ArrayList<Mensaje> mensajes = chat.getMensajes();

        System.out.println("----------------");

        for(Mensaje m : mensajes){

            if(m.getRemitente().equals(usuarioActivo)){

                System.out.printf("%n%s [yo]%n", m.getTexto());

            }else{

                System.out.printf("%n%s [%s]%n",  m.getTexto(), m.getRemitente().getUsername());

            }

        }

        System.out.println("----------------");

    }


    public void mostrarListaChats(ArrayList<Chat> chatsUsuario){

        Usuario usuarioActual = sesion.getUsuarioActivo();

        int i = 0;

        for(Chat c : chatsUsuario){

            i++;

            if(c instanceof ChatPrivado){

                Usuario otroParticipante = ((ChatPrivado) c).getSegundoParticipante(usuarioActual);

                System.out.printf("%d. %s (%s %s)%n", i, otroParticipante.getUsername(), otroParticipante.getNombre(), otroParticipante.getApellido());

            }else if(c instanceof ChatGrupo){

                String nombreGrupo = ((ChatGrupo) c).getNombre();

                System.out.printf("%d. %s%n", i, nombreGrupo);

            }
        }

    }


    private void mostrarListaUsuarios(ArrayList<Usuario> usuarios){
//        ArrayList<Usuario> usuarios = mensajeria.getUsuarios();
        Usuario usuarioActivo = sesion.getUsuarioActivo();


        int i = 0;

        for(Usuario u : usuarios){

            i++;
            System.out.printf("%d. %s (%s %s)%n", i, u.getUsername(), u.getNombre(), u.getApellido());

        }

    }
}
