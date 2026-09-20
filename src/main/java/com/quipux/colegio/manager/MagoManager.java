package com.quipux.colegio.manager;

import com.quipux.colegio.models.MagoEntity;
import java.util.List;

// ¿QUÉ ES UNA INTERFAZ Y QUÉ SON LAS "FIRMAs"?
// Imagina que vas a un restaurante. El menú te dice los platos que puedes pedir (las "firmas" de los métodos),
// pero el menú no te dice CÓMO el chef prepara la comida en la cocina (esa es la implementación).
// Una "firma" es solo el nombre de la función, lo que recibe y lo que devuelve. Ej: void crearMago(MagoEntity mago);
//
// ¿POR QUÉ HACEMOS ESTO Y QUÉ BUENA PRÁCTICA ES?
// Esto se llama "Abstracción". A las demás capas (como el Controlador) no les importa CÓMO el Manager hace su trabajo,
// solo les importa QUÉ puede hacer y que cumpla lo que promete. Esto nos permite cambiar toda la lógica interna
// en el futuro sin que el resto de las clases se rompan. ¡Es una de las reglas de oro de la programación profesional!
public interface MagoManager {
    void crearMago(MagoEntity mago) throws Exception;
    void actualizarMago(Long id, MagoEntity mago) throws Exception;
    void borrarMago(Long id) throws Exception;
    MagoEntity buscarMagoPorId(Long id);
    MagoEntity buscarMagoPorNombre(String nombre);
    List<MagoEntity> buscarTodosLosMagos();
}
