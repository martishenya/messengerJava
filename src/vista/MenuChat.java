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


        char opc;
        int check;
        String username;
        Usuario usuario;

//        limpiarConsola();
        System.out.println("Elige la persona con quien quires empezar a chatear y introduce su username:");

        mostrarListaUsuarios();


        do{

            username = sc.nextLine();
            usuario = mensajeria.buscarUsuario(username);

            if(usuario == null){

                System.out.println("El username introducido no existe en el sistema");
                System.out.println("Probar otra vez?[y/n] ");

                opc = sc.next().charAt(0);
                sc.nextLine();

                if(opc != 'y'){

                    return;
                }

                continue;
            }

            check = mensajeria.crearChatPrivado(sesion.getUsuarioActivo(), usuario);


            if(check == -3){

                System.out.println("Para comunicarte contigo mismo no necesitas una mensajeria :)");
//                pararProg();
                return;

            }

            Chat chat = mensajeria.buscarChatPrivado(sesion.getUsuarioActivo(), usuario);
            if(check == -4){
                System.out.println("Ya tienes el chat con esta persona, te trasladamos al chat..");
//                pararProg();
                abrirChat(chat);
                return;
            }


            abrirChat(chat);
            return;

        }while(true);
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
            System.out.println("2. Volver al menu principal");
//        luego aqui anadimos editar/borrar/etc..

            opc = sc.nextInt();
            sc.nextLine();

            switch(opc){

                case 1:
//                    limpiarConsola();
                    mostrarListaMensajes(chat);
                    enviarMensaje(chat);
                    break;
                case 2:

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


    private void mostrarListaChats(ArrayList<Chat> chatsUsuario){

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


    private void mostrarListaUsuarios(){
        ArrayList<Usuario> usuarios = mensajeria.getUsuarios();
        Usuario usuarioActivo = sesion.getUsuarioActivo();


        for(Usuario u : usuarios){

            if(u.equals(usuarioActivo)){

                continue;

            }

            System.out.printf("%s (%s %s)%n", u.getUsername(), u.getNombre(), u.getApellido());

        }

    }
}
