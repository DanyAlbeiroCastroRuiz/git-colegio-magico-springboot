package com.quipux.colegio.services;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import static org.junit.jupiter.api.Assertions.*;

public class Reto4ServiceIntegrationTest {

    @Test
    public void controllerDebeTenerPathPrincipal() {
        Path path = HechizoService.class.getAnnotation(Path.class);
        assertNotNull(path, "HechizoService debe tener @Path");
        assertEquals("/hechizos", path.value(), "La ruta debe ser /hechizos");
    }

    @Test
    public void metodosDebenTenerVerbosHTTP() throws Exception {
        Method crear = HechizoService.class.getMethod("crearHechizo", com.quipux.colegio.models.HechizoEntity.class);
        assertTrue(crear.isAnnotationPresent(POST.class), "crearHechizo debe ser un @POST");

        Method buscar = HechizoService.class.getMethod("buscarPorTipo", String.class);
        assertTrue(buscar.isAnnotationPresent(GET.class), "buscarPorTipo debe ser un @GET");
        
        Parameter param = buscar.getParameters()[0];
        boolean hasQueryParam = param.getAnnotations().length > 0 && param.getAnnotations()[0].annotationType().getSimpleName().equals("QueryParam");
        assertTrue(hasQueryParam, "El parámetro de buscarPorTipo debe tener @QueryParam");
    }

    @Test
    public void debeManejarExcepcionConBadRequest() throws Exception {
        String code = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get("src/main/java/com/quipux/colegio/services/HechizoService.java")));
        assertTrue(code.contains("catch") && code.contains("Exception"), "Debes usar un bloque try/catch para capturar los errores del Manager");
        assertTrue(code.contains("BAD_REQUEST") || code.contains("400"), "Debes retornar un Status HTTP 400 (Bad Request) si falla la magia");
        assertTrue(code.contains("CREATED") || code.contains("201"), "Debes retornar un Status HTTP 201 (Created) si el hechizo se crea correctamente");
    }
}
