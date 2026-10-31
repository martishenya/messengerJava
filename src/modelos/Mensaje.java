package modelos;
import java.time.LocalDateTime;

public class Mensaje {

    private int id;
    private String texto;
    private LocalDateTime fechaHora;
    private Usuario remitente;
    private boolean leido;
    private boolean editado;

    public Mensaje(int id, String texto, Usuario remitente) {
        this.id = id;
        this.texto = texto;
        this.remitente = remitente;
        fechaHora = LocalDateTime.now();
        leido = false;
        editado = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Usuario getRemitente() {
        return remitente;
    }

    public void setRemitente(Usuario remitente) {
        this.remitente = remitente;
    }

    public boolean isLeido() {
        return leido;
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
    }

    public boolean isEditado() {
        return editado;
    }

    public void setEditado(boolean editado) {
        this.editado = editado;
    }

    public int editarTexto(String text){

        setTexto(text);
        setEditado(true);
        return 0;

    }
}
