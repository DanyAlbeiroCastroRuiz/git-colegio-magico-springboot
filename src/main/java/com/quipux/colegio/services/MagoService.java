package com.quipux.colegio.services;

import com.quipux.colegio.manager.MagoManager;
import com.quipux.colegio.models.MagoEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

// --- ¿QUÉ ES SWAGGER Y POR QUÉ LAS EMPRESAS LO AMAN? ---
// Swagger es una herramienta que lee nuestras anotaciones (@Operation, @Tag) y construye automáticamente una página web 
// hermosa con la documentación de nuestra API para que parezca profesional.
// 
// ¿Para qué se usa? Permite que otros programadores (ej. los que hacen la app móvil en Flutter o el Frontend en React)
// sepan qué rutas existen, qué datos enviar y qué van a recibir, sin tener que saber de Java ni leer nuestro código.
//
// ¿Por qué es importante en el mundo real? Porque las empresas separan sus equipos. Si el Backend (nosotros) no documenta 
// sus "Puertas de acceso", el equipo de Frontend no sabrá cómo conectar la página web con nuestra base de datos.
// Swagger es el estándar mundial de la industria para resolver este problema.

// @Path define el texto que irá en la URL para interactuar con esta clase. Ej: localhost:8080/api/magos
@Path("/magos")
@Produces(MediaType.APPLICATION_JSON) // Le decimos que nuestra API devolverá respuestas en formato JSON
@Consumes(MediaType.APPLICATION_JSON) // Le decimos que esperaremos que el usuario nos mande datos en formato JSON
@Component
// @Tag es exclusivo de Swagger: Agrupa todos estos métodos bajo un "título" bonito en la página web.
@Tag(name = "Magos", description = "Operaciones para gestionar a los magos del colegio")
public class MagoService {

    // Nos traemos el Manager, que es quien tiene las validaciones y reglas de negocio.
    @Autowired
    private MagoManager magoManager;

    // @Operation es exclusivo de Swagger: Le da una descripción a este botón/ruta específico en la página generada.
    // @GET es el método HTTP que se usa en el mundo del desarrollo para "Leer" o "Consultar" información.
    @Operation(summary = "Obtener lista de todos los magos")
    @GET
    public Response obtenerTodosLosMagos() {
        List<MagoEntity> lista = magoManager.buscarTodosLosMagos();
        // Response.ok() equivale a un Código HTTP 200 (Todo salió excelente).
        return Response.ok(lista).build();
    }

    // @Path("/{id}") significa que leeremos un dato variable desde la URL. Ej: /api/magos/5
    @Operation(summary = "Buscar un mago por su ID numérico")
    @GET
    @Path("/{id}")
    public Response obtenerMagoPorId(@PathParam("id") Long id) {
        MagoEntity mago = magoManager.buscarMagoPorId(id);
        
        if (mago != null) {
            return Response.ok(mago).build();
        } else {
            // Si el ID no existe en la base de datos, respondemos con Código HTTP 404 (Not Found / No encontrado).
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    // @POST es el método HTTP que se usa mundialmente para "Crear" un nuevo registro.
    @Operation(summary = "Registrar un mago nuevo en el colegio")
    @POST
    public Response crearMago(MagoEntity mago) {
        try {
            magoManager.crearMago(mago);
            // Si todo sale bien, retornamos un Código HTTP 201 (Created), que es el estándar para creaciones exitosas.
            return Response.status(Response.Status.CREATED).entity("Mago creado con éxito").build();
        } catch (Exception e) {
            // Si el Manager lanza un error (ej: mago sin nombre), retornamos 400 (Bad Request / Petición mala)
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    // @PUT es el método HTTP diseñado para "Actualizar" información que ya existe.
    @Operation(summary = "Actualizar la información de un mago existente")
    @PUT
    @Path("/{id}")
    public Response actualizarMago(@PathParam("id") Long id, MagoEntity mago) {
        try {
            magoManager.actualizarMago(id, mago);
            return Response.ok("Mago actualizado correctamente").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    // @DELETE es el método HTTP diseñado, como su nombre lo indica, para eliminar datos.
    @Operation(summary = "Eliminar un mago del colegio por su ID")
    @DELETE
    @Path("/{id}")
    public Response eliminarMago(@PathParam("id") Long id) {
        try {
            magoManager.borrarMago(id);
            // Código 204 (No Content) es ideal para borrar. Indica que salió bien pero ya no hay contenido qué mostrar.
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
