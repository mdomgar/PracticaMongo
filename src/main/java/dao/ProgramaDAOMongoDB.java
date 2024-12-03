package dao;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.internal.bulk.UpdateRequest;
import modelo.Audiencia;
import modelo.Programa;
import org.bson.Document;
import org.bson.types.ObjectId;
import utils.MongoDBConnection;

import javax.print.Doc;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProgramaDAOMongoDB implements ProgramaDAO {
    private MongoCollection<Document> collection;

    public ProgramaDAOMongoDB(MongoDBConnection connection) {
        collection = connection.getDatabase().getCollection("programas"); //Obtengo la conexión con MongoDB
    }

    @Override
    public void insertar(Programa programa) {
        Document documento = programa.toDocument();
        collection.insertOne(documento); //Inserto el documento Programa
        System.out.println("Insertado con éxito.");
    }

    @Override
    public Programa buscarPorId(String id) {
        ObjectId objectId = new ObjectId(id);

        Document filter = new Document("_id", objectId);

        FindIterable<Document> result = collection.find(filter); //Obtengo la id, filtro y la encuentro

        Document document = result.first();

        if (document != null) {
            return Programa.fromDocument(document);
        } else {
            return null;
        }
    }

    public List<Programa> buscarPorNombre(String nombre) {

        Document filter = new Document("nombre", nombre);

        FindIterable<Document> result = collection.find(filter); //Lo mismo con id pero con nombre

        List<Programa> programas = new ArrayList<>();

        for (Document doc : collection.find()) {
            programas.add(Programa.fromDocument(doc));
        }
        return programas;
    }

    @Override
    public List<Programa> buscarTodos() {
        List<Programa> programas = new ArrayList<>();
        for (Document document : collection.find()) { //Busco todo lo que hay en la bd y lo muestro
            programas.add(Programa.fromDocument(document));
        }
        return programas;
    }

    @Override
    public void actualizarById(Programa programa, String id) {
        ObjectId objectId = new ObjectId(id);

        Document filter = new Document("_id", objectId);

        Document updateFields = new Document("$set", programa.toDocument()); //Filtro por la id y actualizo el programa

        UpdateResult result = collection.updateOne(filter, updateFields);

        if (result.getMatchedCount() > 0) {
            System.out.println("Programa actualizado correctamente");
        } else {
            System.out.println("No se ha encontrado la id proporcionada");
        }
    }

    @Override
    public void eliminar(String id) {
        ObjectId objectId = new ObjectId(id);

        Document filter = new Document("_id", objectId);

        DeleteResult result = collection.deleteOne(filter);

        if (result.getDeletedCount() > 0) {
            System.out.println("Programa eliminado correctamente");
        } else {
            System.out.println("No se ha encontrado la id proporcionada");
        }
    }

    @Override
    public List<Programa> buscarPorCategoria(String categoria) {

        Document filter = new Document("categoria", categoria);

        FindIterable<Document> programas = collection.find(filter);

        List<Programa> result = new ArrayList<>();

        for (Document doc : programas) {
            Programa programa = Programa.fromDocument(doc);
            result.add(programa);
        }

        return result;
    }

    @Override
    public Programa programaConMayorAudiencia(String fecha) { //Filtro por la fecha, lo ordeno y cojo el de mayor audiencia
        Document matchStage = new Document("$match", new Document("audiencias.fecha", fecha));

        AggregateIterable<Document> resultados = collection.aggregate(Arrays.asList(
                matchStage,
                new Document("$unwind", "$audiencias"),
                new Document("$match", new Document("audiencias.fecha", fecha)),
                new Document("$group", new Document("_id", "$nombre")
                        .append("audienciaTotal", new Document("$sum", "$audiencias.espectadores"))),
                new Document("$sort", new Document("audienciaTotal", -1)),
                new Document("$limit", 1)
        ));


        if (resultados.iterator().hasNext()) {
            Document doc = resultados.iterator().next();
            String nombrePrograma = doc.getString("_id");

            Document programaDoc = collection.find(new Document("nombre", nombrePrograma)).first();
            if (programaDoc != null) {
                return Programa.fromDocument(programaDoc);
            }
        }

        return null;
    }

    @Override
    public double calcularAudienciaMedia(String id, String fechaInicio, String fechaFin) { //Obtengo el programa con esa id y comparo las fechas, una vez comparadas hago las medias con los espectadores
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //Entre esas fechas
        LocalDate inicioDate = LocalDate.parse(fechaInicio, formatter);
        LocalDate finDate = LocalDate.parse(fechaFin, formatter);

        ObjectId objectId = new ObjectId(id);

        Document filter = new Document("_id", objectId);
        Document programaDoc = collection.find(filter).first();

        if (programaDoc == null) {
            return 0.0;
        }

        Programa programa = Programa.fromDocument(programaDoc);

        List<Audiencia> audienciasFiltradas = new ArrayList<>();
        for (Audiencia audiencia : programa.getAudiencias()) {
            LocalDate audienciaFecha = LocalDate.parse(audiencia.getFecha(), formatter);

            if ((audienciaFecha.isEqual(inicioDate) || audienciaFecha.isAfter(inicioDate)) &&
                    (audienciaFecha.isEqual(finDate) || audienciaFecha.isBefore(finDate))) {
                audienciasFiltradas.add(audiencia);
            }
        }

        if (audienciasFiltradas.isEmpty()) {
            return 0.0;
        }

        int audienciaTotal = 0;
        for (Audiencia audiencia : audienciasFiltradas) {
            audienciaTotal += audiencia.getEspectadores();
        }

        return (double) audienciaTotal / audienciasFiltradas.size();
    }
}

