package com.quipux.colegio.manager;

import com.quipux.colegio.models.HechizoEntity;
import java.util.List;

public interface HechizoManager {
    HechizoEntity registrarHechizo(HechizoEntity hechizo) throws Exception;
    List<HechizoEntity> buscarMagia(String tipoMagia);
}
