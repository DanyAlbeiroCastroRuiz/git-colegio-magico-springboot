package com.quipux.colegio.manager;

import com.quipux.colegio.models.HechizoEntity;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

public class Reto3ManagerTest {

    @Test
    public void managerDebeSerUnServicio() {
        assertTrue(HechizoManagerImpl.class.isAnnotationPresent(Service.class), "Falta la anotación @Service");
        assertTrue(HechizoManagerImpl.class.isAnnotationPresent(Transactional.class), "Falta la anotación @Transactional en la clase o método");
    }

    @Test
    public void prohibidoGuardarNombreVacio() {
        HechizoManagerImpl manager = new HechizoManagerImpl();
        HechizoEntity invalido = new HechizoEntity();
        invalido.setNombre("");
        
        Exception ex = assertThrows(Exception.class, () -> {
            manager.registrarHechizo(invalido);
        });
        
        assertEquals("Nombre invalido", ex.getMessage(), "Debe validar que el nombre no venga nulo ni vacío");
    }

    @Test
    public void prohibidoGuardarMagiaOscura() {
        HechizoManagerImpl manager = new HechizoManagerImpl();
        HechizoEntity oscura = new HechizoEntity();
        oscura.setTipoMagia("Oscura");
        
        Exception ex = assertThrows(Exception.class, () -> {
            manager.registrarHechizo(oscura);
        });
        
        assertEquals("Magia prohibida en el colegio", ex.getMessage(), "El mensaje de error debe ser exactamente el indicado");
    }
}
