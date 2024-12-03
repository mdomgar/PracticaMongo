package modelo;

import org.bson.Document;

import java.time.LocalDate;
import java.time.LocalTime;

public class Audiencia {
    private String fecha;
    private int espectadores;

    public Audiencia(String fecha, int espectadores) {
        this.fecha = fecha;
        this.espectadores = espectadores;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getEspectadores() {
        return espectadores;
    }

    public void setEspectadores(int espectadores) {
        this.espectadores = espectadores;
    }

    public Document toDocument() {
        return new Document("fecha", fecha)
                .append("espectadores", espectadores);
    }

    public static Audiencia fromDocument(Document document) {
        String fecha = document.getString("fecha");
        int espectadores = document.getInteger("espectadores");
        return new Audiencia(fecha, espectadores);
    }

    @Override
    public String toString() {
        return "Audiencia{" +
                "fecha='" + fecha + '\'' +
                ", espectadores=" + espectadores +
                '}';
    }
}
