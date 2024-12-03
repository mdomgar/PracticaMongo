package modelo;

import org.bson.Document;

import java.util.List;
import java.util.stream.Collectors;

public class Programa {
    private String nombre;
    private String categoria;
    private Horario horario;
    private List<Audiencia> audiencias;
    private List<Colaborador> colaboradores;

    public Programa(String nombre, String categoria, Horario horario, List<Audiencia> audiencias, List<Colaborador> colaboradores) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.horario = horario;
        this.audiencias = audiencias;
        this.colaboradores = colaboradores;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public List<Audiencia> getAudiencias() {
        return audiencias;
    }

    public void setAudiencias(List<Audiencia> audiencias) {
        this.audiencias = audiencias;
    }

    public List<Colaborador> getColaboradores() {
        return colaboradores;
    }

    public void setColaboradores(List<Colaborador> colaboradores) {
        this.colaboradores = colaboradores;
    }

    public List<Document> convertAudienciaToDocument() { //Metodo para convertir la lista en documento
        return audiencias.stream()
                .map(audiencia -> audiencia.toDocument())
                .toList();
    }

    public List<Document> convertColaboradorToDocument() { //Metodo para convertir la lista en documento
        return colaboradores.stream()
                .map(colaborador -> colaborador.toDocument())
                .toList();
    }

    public Document toDocument() {
        return new Document("nombre", nombre)
                .append("categoria", categoria)
                .append("horario", horario.toDocument())
                .append("audiencias", convertAudienciaToDocument())
                .append("colaboradores", convertColaboradorToDocument());
    }

    public static Programa fromDocument(Document document) { //Hacemos un stream para que cado objeto se desdocumente y se convierta
        String nombre = document.getString("nombre");
        String categoria = document.getString("categoria");
        Document horarioDoc = (Document) document.get("horario");
        Horario horario = Horario.fromDocument(horarioDoc);
        List<Audiencia> audiencias = ((List<Document>) document.get("audiencias")).stream()
                .map(Audiencia::fromDocument)
                .collect(Collectors.toList());
        List<Colaborador> colaboradores = ((List<Document>) document.get("colaboradores")).stream()
                .map(Colaborador::fromDocument)
                .collect(Collectors.toList());
        return new Programa(nombre, categoria, horario, audiencias, colaboradores);
    }

    @Override
    public String toString() {
        return "Programa{" +
                "nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                ", horario=" + horario +
                ", audiencias=" + audiencias +
                ", colaboradores=" + colaboradores +
                '}';
    }
}
