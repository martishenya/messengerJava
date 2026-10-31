package servicio;

import modelos.*;

import java.util.ArrayList;

public class GestorMensajeria {

    private ArrayList<Usuario> usuarios;
    private ArrayList<Chat> chats;

    public GestorMensajeria() {
        usuarios = new ArrayList<>();
        chats = new ArrayList<>();
    }

    public Usuario buscarUsuario(String username){

        for(Usuario u : usuarios){

            if(u.getUsername().equalsIgnoreCase(username)){
                return u;
            }

        }

        return null;

    }

    public int crearUsuario( String nombre, String apellido, String username, String contrasena){

        int id = 0;

        if(buscarUsuario(username) == null){

            Usuario usuario = new Usuario(id, nombre, apellido, username, contrasena);
            usuarios.add(usuario);

            return 0;

        }else{

            return -1;
//            ERROR: ya existe el username introducido

        }

//        para ahora estos codigos de error no sirven en practica pero lo dejamos por seguridad de la lógica
//        teniendo en cuenta que tenemos las comprobaciones en la parte de menu
//        asi separamos la logica de ui y logica interna del prorgama
    }

    public int eliminarUsuario(String username){

        Usuario usuario = buscarUsuario(username);

        if(usuario != null){

            usuarios.remove(usuario);
//            потом можно/нужно еще удалять приватные чаты с удаленным пользователем
            return 0;
        }else{

            return -2;
//            ERROR: no existe el usuario con el username introducido que hay que eliminar

        }

    }


    public int crearChatPrivado(Usuario usuario1, Usuario usuario2){

        if(usuario1.equals(usuario2)){

          return -3;
//            ERROR: no se puede crear chatPrivado con usuarios iguales

        }else if(existeChatPrivado(usuario1, usuario2)){

          return -4;
//            ERROR: el chatPrivado entre usuarios ya existe

        }


        ChatPrivado chatPriv = new ChatPrivado(0, usuario1, usuario2);
        chats.add(chatPriv);

        return 0;

    }

    public ArrayList<Usuario> getUsuarios(){
        return new ArrayList<>(usuarios);

//        hacemos asi para no pasar la lista original fuera y no tener acceso a ella desde fuera
    }


    public ArrayList<Chat> buscarChatsPorUsuario(Usuario usuario){

        ArrayList<Chat> chatsUsuario = new ArrayList<>();

        for(Chat c : chats){

            if(c.contieneUsuario(usuario)){

                chatsUsuario.add(c);

            }

        }

        return chatsUsuario;

    }

    public ChatPrivado buscarChatPrivado(Usuario usuario1, Usuario usuario2){

        ArrayList<Chat> chatsUsuario = buscarChatsPorUsuario(usuario1);

        for(Chat c : chatsUsuario){

            if(c instanceof ChatPrivado && c.contieneUsuario(usuario2) ){

                return ((ChatPrivado) c);

            }


        }


        return null;
    }


    private boolean existeChatPrivado(Usuario usuario1, Usuario usuario2){

        return buscarChatPrivado(usuario1, usuario2) != null;
    }

    public void enviarMensaje(Chat chat, Usuario remitente, String texto){

        Mensaje mensaje = new Mensaje(0, texto, remitente);

        chat.anadirMensaje(mensaje);

    }

}
