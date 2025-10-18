package Ejemplo;
public class Documento {
    private final int id;
    private final String contenido;

    public Documento(int id, String contenido) {
        this.id = id;
        this.contenido = contenido;
    }

    public int getId() {
        return id;
    }

    public String getContenido() {
        return contenido;
    }

    @Override
    public String toString() {
        return "Doc " + id + ": " + contenido;
    }
}