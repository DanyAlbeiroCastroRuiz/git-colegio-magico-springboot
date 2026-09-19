# 🧙‍♂️ Reto 0: Guía del Profesor (El Mago)

Este es el reto que programarás en vivo. La idea es que les enseñes desde cero (File > New > Java Class) en el paquete `models`, `dao`, `manager` y `services`, y hagas las comparaciones con PHP para que entiendan la magia de Spring Boot y Jakarta.

No dejaremos el código pre-creado; tú serás quien guíe la creación paso a paso.

## 1. La Capa Models (`MagoEntity.java`)
Crea la clase y explica:
* **PHP:** En PHP (sin ORM) crearías la tabla a mano con SQL y usarías un array asociativo. Si usas Eloquent, creas un modelo que asume el nombre de la tabla.
* **Spring:** Usamos anotaciones para decirle a Hibernate cómo construir y mapear la tabla automáticamente.

**Código a escribir en vivo:**
```java
package com.quipux.colegio.models;

import jakarta.persistence.*;

@Entity
@Table(name = "magos") // Explica: Mapeo explícito a la tabla
public class MagoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Explica: AUTO_INCREMENT
    private Long id;
    
    @Column(name = "nombre_mago", nullable = false) // Explica: Restricciones NOT NULL
    private String nombre;
    
    // Genera Getters y Setters con tu IDE
}
```

## 2. La Capa DAO (`MagoDaoImpl.java`)
Crea la clase y explica:
* **PHP:** En PHP usarías `PDO`, harías un `$stmt = $pdo->prepare("INSERT...")` y luego `$stmt->execute()`.
* **Spring:** El `EntityManager` hace todo por debajo. Para guardar solo llamamos a `persist()`. Para consultar, creamos el Query y usamos `setParameter` igual que el `bindValue` en PDO para evitar Inyección SQL.

**Código a escribir en vivo:**
```java
package com.quipux.colegio.dao;

import com.quipux.colegio.models.MagoEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository; // Explica: Lo registra como un Bean de persistencia

@Repository
public class MagoDaoImpl {

    @PersistenceContext
    private EntityManager entityManager;

    public void guardar(MagoEntity mago) {
        entityManager.persist(mago); // Equivale a un INSERT INTO
    }

    public MagoEntity buscarPorNombre(String nombre) {
        // NUNCA concatenar. Usar :nombre
        Query query = entityManager.createQuery("SELECT m FROM MagoEntity m WHERE m.nombre = :nombre");
        query.setParameter("nombre", nombre);
        return (MagoEntity) query.getResultList().get(0); // Simplificado para el ejemplo
    }
}
```

## 3. La Capa Manager (`MagoManagerImpl.java`)
Crea la clase y explica:
* **PHP:** Aquí iría tu controlador tradicional de PHP o tu capa de servicios donde validas si `$_POST['nombre']` no viene vacío.
* **Spring:** Usamos `@Service` y manejamos excepciones. También usamos `@Transactional` para asegurar que si algo falla, no se guarde basura en la BD (rollback).

**Código a escribir en vivo:**
```java
package com.quipux.colegio.manager;

import com.quipux.colegio.dao.MagoDaoImpl;
import com.quipux.colegio.models.MagoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MagoManagerImpl {

    @Autowired // Explica: Inyección de dependencias (Spring te da la instancia)
    private MagoDaoImpl magoDao;

    @Transactional
    public void registrarMago(MagoEntity mago) throws Exception {
        if (mago.getNombre() == null || mago.getNombre().isEmpty()) {
            throw new Exception("El mago debe tener un nombre");
        }
        magoDao.guardar(mago);
    }
}
```

## 4. La Capa Services (`MagoService.java` - Jakarta)
Crea la clase y explica:
* **PHP:** En un script PHP clásico, harías `if ($_SERVER['REQUEST_METHOD'] === 'POST')` y leerías `$_POST` o `file_get_contents('php://input')`.
* **Spring/Jakarta:** Las anotaciones `@POST` y `@GET` rutean las peticiones. Spring convierte automáticamente el JSON que envía el cliente al objeto `MagoEntity`.

**Código a escribir en vivo:**
```java
package com.quipux.colegio.services;

import com.quipux.colegio.manager.MagoManagerImpl;
import com.quipux.colegio.models.MagoEntity;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;

@Path("/magos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MagoService {

    @Autowired
    private MagoManagerImpl manager;

    @POST // Explica: En PHP sería $_POST
    public Response crearMago(MagoEntity mago) {
        try {
            manager.registrarMago(mago);
            return Response.status(Response.Status.CREATED).entity(mago).build(); // 201 Created
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build(); // 400 Bad Request
        }
    }
}
```

Al terminar de codificar esto en vivo con ellos, habrán entendido la estructura y estarán listos para los Retos 1 al 4 (Hechizos).
