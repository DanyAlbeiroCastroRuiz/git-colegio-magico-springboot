package com.quipux.colegio.dao;

import com.quipux.colegio.models.HechizoEntity;
import java.util.List;

public interface HechizoDao {
    HechizoEntity guardarHechizo(HechizoEntity hechizo);
    List<HechizoEntity> buscarPorTipo(String tipoMagia);
    HechizoEntity buscarPorNombre(String nombre);
}
