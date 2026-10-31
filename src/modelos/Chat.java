package modelos;

import java.util.ArrayList;

public abstract class Chat {

    private int id;
    private ArrayList<Usuario> participantes;
    private ArrayList<Mensaje> mensajes;

    public Chat(int id) {
        this.id = id;
        participantes = new ArrayList<Usuario>();
        mensajes = new ArrayList<Mensaje>();
    }

    public int anadirMensaje(Mensaje mensaje){

        mensajes.add(mensaje);
        return 0;
    }

    public int eliminarMensaje(Mensaje mensaje){

        mensajes.remove(mensaje);
        return 0;
    }

    public ArrayList<Usuario> getParticipantes(){

//        aqui no enviamos el link del array original, enviamos nuevo array para no tener acceso al original desde fuera
        return new ArrayList<>(participantes); //плохо возвращать сам array
    }

    public ArrayList<Mensaje> getMensajes(){

        return new ArrayList<>(mensajes);
    }

    public boolean contieneUsuario(Usuario usuario){

        return participantes.contains(usuario);
    }

    protected int anadirParticipante(Usuario usuario){
        participantes.add(usuario);
        return 0;
    }


}
