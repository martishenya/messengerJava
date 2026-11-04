package modelos;

import java.util.ArrayList;

public class ChatGrupo extends Chat{

    private String nombre;
    private Usuario usuarioCreador;
//    private ArrayList<Usuario> admins;


    public ChatGrupo(int id, String nombre, Usuario usuarioCreador) {
        super(id);
        this.nombre = nombre;
        this.usuarioCreador = usuarioCreador;
        anadirParticipante(usuarioCreador);


    }



    public int anadirParticipantes(ArrayList<Usuario> usuarios){

        for(Usuario u : usuarios){

            anadirParticipante(u);

            //podemos ahora tener el creador en la lista
        }

        return 0;

    }

    public String getNombre() {
        return nombre;
    }
}
