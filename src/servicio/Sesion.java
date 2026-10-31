package servicio;

import modelos.Usuario;

public class Sesion {

    private Usuario usuarioActivo;

    public Sesion() {
        this.usuarioActivo = null;
    }

    public void iniciarSesion(Usuario usuarioAActivar){

        this.usuarioActivo = usuarioAActivar;

    }

    public void cerrarSesion(){

        usuarioActivo = null;

    }

    public Usuario getUsuarioActivo() {
        return usuarioActivo;
    }
}
