package modelos;

public class ChatPrivado extends Chat {

    public ChatPrivado(int id, Usuario usuario1, Usuario usuario2) {
        super(id);
        anadirParticipante(usuario1);
        anadirParticipante(usuario2);

    }

    public Usuario getSegundoParticipante(Usuario usuarioActivo){

        for(Usuario u : getParticipantes()){

            if(!u.equals(usuarioActivo)){

                return u;

            }

        }

        return null;
    }



}
