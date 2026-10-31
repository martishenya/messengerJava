package modelos;
import java.time.LocalDateTime;
import java.util.Objects;

public class Usuario {

    private int id;
    private String nombre;
    private String apellido;
    private String username;
    private String contrasena;
    private LocalDateTime fechaHoraRegistro;

    public Usuario(int id, String nombre, String apellido, String username, String contrasena) {

        this.id = id; //в идеале айдишник генерировать рандомно, добавить потом
        this.nombre = nombre;
        this.apellido = apellido;
        this.username = username;
        this.contrasena = contrasena;
        fechaHoraRegistro = LocalDateTime.now();

    }

    public String getUsername() {
        return username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getApellido() {
        return apellido;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(username, usuario.username);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(username);
    }
}
