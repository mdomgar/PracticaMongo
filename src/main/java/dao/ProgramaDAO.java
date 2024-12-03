package dao;

import modelo.Programa;

import java.util.List;

public interface ProgramaDAO {
    void insertar(Programa programa);
    Programa buscarPorId(String id);
    List<Programa> buscarTodos();
    void actualizarById(Programa programa, String id);
    void eliminar(String id);
    List<Programa> buscarPorCategoria(String categoria);
    Programa programaConMayorAudiencia(String fecha);
    double calcularAudienciaMedia(String id, String fechaInicio, String fechaFin);
}
