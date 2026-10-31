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


//    поменять потом в гитхабе в списке кейсов что можно добавить участника или участников в группу
//    не только одного в смысле
    public int anadirParticipantes(ArrayList<Usuario> usuarios){

        for(Usuario u : usuarios){

            anadirParticipante(u);
//нюанс что может попасть создатель группы в список -- поправить потом
        }

        return 0;

    }

    public String getNombre() {
        return nombre;
    }
}
