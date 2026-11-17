package vista;

import modelos.*;
import servicio.Builder;
import servicio.GestorMensajeria;
import servicio.Sesion;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

    private Scanner sc;

    private GestorMensajeria mensajeria;

    private Sesion sesion;

    private MenuChat menuChat;

    public Menu() {

        sc = new Scanner(System.in);

        mensajeria = new GestorMensajeria();

        sesion = new Sesion();

        Builder.datosEjemplo(mensajeria);

        menuChat = new MenuChat(sc, mensajeria, sesion);

    }

    public void iniciarMensajeria(){

        int opc;

        while (true){

//            limpiarConsola();

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
//                        pararProg();
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
        System.out.println("4. Ajustes");
        System.out.println("0. Cerrar sesion");

        opc = sc.nextInt();
        sc.nextLine();

        switch (opc) {

            case 1:
                menuChat.crearChatPrivado();
                break;
            case 2:
                menuChat.crearChatGrupo();
                break;
            case 3:
                menuChat.misChats();
                break;
            case 4:
                ajustes();
                break;
            case 0:
                sesion.cerrarSesion();
                return;
            default:
                System.out.println("El valor introducido no es valido, prueba otra vez!");
//                pararProg();
                break;
        }

    }


    private void registrarse(){

        String nombre, apellido, username, contrasena;

        Usuario checkUsuario;
        char opc;

//        limpiarConsola();

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

            // next() en el lugar de nextLine() para evitar error si el usuario introduce una línea vacía
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
//            limpiarConsola();
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


    private void ajustes(){

        int opc;
        Usuario usuarioActivo = sesion.getUsuarioActivo();

        System.out.println("Elige la accion:");
        System.out.println("1. Eliminar el perfil actual");
        System.out.println("2. Eliminar un chat o un grupo(creado por ti mismo)");
        System.out.println("3. Cambiar la contrasena");
//        System.out.println("0. Volver");

        opc = sc.nextInt();
        sc.nextLine();

        switch (opc){

            case 1:
                mensajeria.eliminarUsuario(usuarioActivo);
                sesion.cerrarSesion();
                break;

            case 2:

                eliminarChat();
                break;

            case 3:
                String contrasenaAct, contrasenaNuev;
                int check;

                System.out.println("Introduce la contrasena actual:");
                contrasenaAct = sc.nextLine();
                System.out.println("Introduce la contrasena nueva:");
                contrasenaNuev = sc.nextLine();

                check = mensajeria.modificarContrasena(usuarioActivo,contrasenaNuev,contrasenaAct);
                if(check == -5){

                    System.out.println("La contrasena actual introducida es incorrecta");
//                    pararProg();
                    return;
                }else{

                    System.out.println("La contrasena fue actualizada correctamente");
//                    pararProg();
                    return;

                }

            default:

        }
    }

    private void eliminarChat(){

        ArrayList<Chat> chats = mensajeria.buscarChatsPorUsuario(sesion.getUsuarioActivo(), true);
        int opc;

        if(chats.isEmpty()){
            System.out.println("No tienes chats o grupos que puedas eliminar");
            return;
        }

        System.out.println("Elige el chat que quieres eliminar y introduce el numero correspondiente:");
        menuChat.mostrarListaChats(chats);

        do{

            opc = sc.nextInt();
            sc.nextLine();

            if(opc >= 1 && opc <= chats.size()){

                Chat chat = chats.get(opc - 1);
                mensajeria.eliminarChat(chat);

                return;
            }

            System.out.println("El valor introducido no es valido, prueba otra vez!");


        }while(true);



    }

//para hacer la interacion mas guay usamos este metodo en el inicio de cada paso para limpiar la consola
//    por partes significa:
//    \033 - el codigo que significa que tras de el tenemos comando ANSI
//    [H - ponemos el cursor al inicio
//    [2J - limpia la consola
//    flush - para enviar todo lo queda en el bufer

//    private void limpiarConsola(){
//
//        System.out.print("\033[H\033[2J");
//        System.out.flush();
//    }

    //        encontramos esta secuencia para parar por un poco el programa, porque si no lo usamos
//    el usuario no va a ver los mensajes de error a causa de limpiarConsola()



//    private void pararProg(){
//
//        try{
//            Thread.sleep(1500);
//        }catch(InterruptedException e){
//            Thread.currentThread().interrupt();
//        }
//    }

}
