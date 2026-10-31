package vista;

import modelos.*;
import servicio.GestorMensajeria;
import servicio.Sesion;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

    private Scanner sc;

    private GestorMensajeria mensajeria;

    private Sesion sesion;

    public Menu() {

        sc = new Scanner(System.in);

        mensajeria = new GestorMensajeria();

        sesion = new Sesion();

    }

    public void iniciarMensajeria(){

        int opc;

        while (true){

            limpiarConsola();

            if (sesion.getUsuarioActivo() != null) {
                menuPrincipal();

            } else {

                System.out.println("\n--- Bienvenido! ---");
                System.out.println("1. Registrarse");
                System.out.println("2. Autenticarse");
                System.out.println("0. Salir de la mensajeria");

                opc = sc.nextInt();
                sc.nextLine();

                switch (opc) {

                    case 1:
                        registrarse();
                        break;
                    case 2:
                        autenticarse();
                        break;
                    case 0:
                        System.out.println("Hasta luego!!");
                        return;
                    default:
                        System.out.println("El valor introducido no es valido, prueba otra vez!");
                        pararProg();
                        break;
                }
            }
        }
    }


    private void menuPrincipal(){

        int opc;

        System.out.printf("%n-----%s(online)%n", sesion.getUsuarioActivo().getNombre());
        System.out.println("1. Crear chat privado");
        System.out.println("2. Crear grupo");
        System.out.println("3. Mis chats");
        System.out.println("4. Ajustes(redactar el perfil/ redactar tus grupos");
        System.out.println("0. Cerrar sesion");

        opc = sc.nextInt();
        sc.nextLine();

        switch (opc) {

            case 1:
                crearChatPrivado();
                break;
            case 2:
//                crearChatGrupo();
                break;
            case 3:
                misChats();
                break;
            case 4:

                break;
            case 0:
                sesion.cerrarSesion();
                return;
            default:
                System.out.println("El valor introducido no es valido, prueba otra vez!");
                pararProg();
                break;
        }

    }


    private void registrarse(){

        String nombre, apellido, username, contrasena;

        Usuario checkUsuario;
        char opc;

        limpiarConsola();

        System.out.println("\n--- REGISTRARSE ---");
        System.out.println("Escribe tu nombre");
        nombre = sc.nextLine();
        System.out.println("Escribe tu apellido");
        apellido = sc.nextLine();
        do{

            System.out.println("Escribe tu username para la mensajeria");
            username = sc.nextLine();
            checkUsuario = mensajeria.buscarUsuario(username);

            if(checkUsuario == null){

                System.out.println("Escribe la contrasena para tu cuenta");
                contrasena = sc.nextLine();
                mensajeria.crearUsuario(nombre, apellido, username, contrasena);
                sesion.iniciarSesion(mensajeria.buscarUsuario(username));
                return;

            }

            System.out.println("El username introducido ya existe");
            System.out.println("Probar otra vez?[y/n] ");
            opc = sc.next().charAt(0);
            sc.nextLine();


//            cualquier char exepto 'y' va a saltarnos atras
            if(opc != 'y'){

                return;
            }

        }while(true);
    }


    private void autenticarse(){

        String username, contrasena;
        char opc;

        do{
            limpiarConsola();
            System.out.println("\n--- AUTENTICACION ---");
            System.out.println("Escribe tu username");
            username = sc.nextLine();

            Usuario usuario = mensajeria.buscarUsuario(username);

            if(usuario != null){

                do{

                    System.out.printf("%nHola %s%n", usuario.getNombre());
                    System.out.println("Escribe la contrasena de tu cuenta");
                    contrasena = sc.nextLine();

                    if(usuario.getContrasena().equals(contrasena)){

                        sesion.iniciarSesion(usuario);
                        return;


                    }else{

                        System.out.println("La xontrasena introducida no coincide");
                        System.out.println("Probar otra vez?[y/n] ");

                        opc = sc.next().charAt(0);
                        sc.nextLine();

//                        cualquier char exepto 'y' va a saltarnos atras
                        if(opc != 'y'){

                            return;
                        }
                    }

                }while(true);

            }else{

                System.out.println("El username introducido no existe en el sistema");
                System.out.println("Probar otra vez?[y/n] ");
                System.out.println("Si quieres registrarse: [r] ");

                opc = sc.next().charAt(0);
                sc.nextLine();

                if(opc == 'r'){
                    registrarse();
//                    muy importante anadir return al final para que no quedemos en el bucle actual
                    return;
                }else if(opc != 'y'){
                    return;
                }
            }

        }while(true);
    }

    private void crearChatPrivado(){


        char opc;
        int check;
        String username;
        Usuario usuario;

        limpiarConsola();
        System.out.println("Elige la persona con quien quires empezar a chatear y introduce su username:");
//        System.out.println(mostrarListaUsuarios());
        mostrarListaUsuarios();
//        cambiar luego: no hay que mostrar el usuario activo

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
                pararProg();
                return;

            }

            Chat chat = mensajeria.buscarChatPrivado(sesion.getUsuarioActivo(), usuario);
            if(check == -4){
                System.out.println("Ya tienes el chat con esta persona, te trasladamos al chat..");
                pararProg();
                abrirChat(chat);
                return;
            }


            abrirChat(chat);
//            вот тут потом сделать вызов уже в сам чат, чтобы можно было общаться!!
            return;

        }while(true);
    }




    private void misChats(){

        int opc;
        Usuario usuarioActual = sesion.getUsuarioActivo();
        ArrayList<Chat> chatsUsuario = mensajeria.buscarChatsPorUsuario(usuarioActual);

        if(chatsUsuario.isEmpty()){

            System.out.println("Todavia no tienes ningun chat ");
            pararProg();
            return;

        }

        limpiarConsola();
        System.out.println("Elige un chat/grupo para seguir chateando: ");
        mostrarListaChats(chatsUsuario);

        do{

            opc = sc.nextInt();
            sc.nextLine(); //норм не?

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
            limpiarConsola();
            mostrarListaMensajes(chat);

            System.out.println("Elige la accion:");
            System.out.println("1. Enviar mensaje");
            System.out.println("2. Volver al menu principal");
//        luego aqui anadimos editar/borrar/etc..

            opc = sc.nextInt();
            sc.nextLine();

            switch(opc){

                case 1:
                    limpiarConsola();
                    mostrarListaMensajes(chat);
                    enviarMensaje(chat);
                    break;
                case 2:

                    return;
                default:
                    System.out.println("El valor introducido no es valido, prueba otra vez!");



                    pararProg();

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

//para hacer la interacion mas guay usamos este metodo en el inicio de cada paso para limpiar la consola
//    por partes significa:
//    \033 - el codigo que significa que tras de el tenemos comando ANSI
//    [H - ponemos el cursor al inicio
//    [2J - limpia la consola
//    flush - para enviar todo lo queda en el bufer

    private void limpiarConsola(){

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    //        encontramos esta secuencia para parar por un poco el programa, porque si no lo usamos
//    el usuario no va a ver los mensajes de error a causa de limpiarConsola()

    private void pararProg(){

        try{
            Thread.sleep(1500);
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

}
