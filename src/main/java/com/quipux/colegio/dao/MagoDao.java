package com.quipux.colegio.dao;

import com.quipux.colegio.models.MagoEntity;
import java.util.List;

// El DAO (Data Access Object) es el encargado EXCLUSIVO de hablar con la Base de Datos.
// Esta interfaz es como un índice de un libro: nos dice QUÉ puede hacer el DAO, pero no nos dice CÓMO lo hace.
public interface MagoDao {
    void insert(MagoEntity mago);
    void update(MagoEntity mago);
    void delete(Long id);
    MagoEntity findById(Long id);
    MagoEntity findByName(String nombre);
    List<MagoEntity> findAll();
}
