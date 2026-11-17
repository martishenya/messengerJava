package modelos;

import java.util.ArrayList;

public class ChatGrupo extends Chat{

    private String nombre;
    private Usuario usuarioCreador;
//    private ArrayList<Usuario> admins;


    public ChatGrupo(int id, String nombre, Usuario usuarioCreador, ArrayList<Usuario> usuarios) {
        super(id);
        this.nombre = nombre;
        this.usuarioCreador = usuarioCreador;
        anadirParticipante(usuarioCreador);

        anadirParticipantes(usuarios);

    }



    //    garantizamos que no se puede anadir el creador en la lista por las comprobaciones que estan fuera
    public int anadirParticipantes(ArrayList<Usuario> usuarios){

        for(Usuario u : usuarios){

            anadirParticipante(u);

        }

        return 0;

    }

    public String getNombre() {
        return nombre;
    }

    public Usuario getUsuarioCreador() {
        return usuarioCreador;
    }
}
