package modelo;

import org.bson.Document;

import java.time.LocalTime;

public class Horario {
    private int dia;
    private String hora;

    public Horario(int dia, String hora) {
        this.dia = dia;
        this.hora = hora;
    }

    public Integer getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Document toDocument(){
        return new Document("dia", dia)
                .append("hora", hora);
    }

    public static Horario fromDocument(Document document) {
        int dia = document.getInteger("dia");
        String hora = document.getString("hora");
        return new Horario(dia, hora);
    }

    @Override
    public String toString() {
        return "Horario{" +
                "dia=" + dia +
                ", hora='" + hora + '\'' +
                '}';
    }
}
