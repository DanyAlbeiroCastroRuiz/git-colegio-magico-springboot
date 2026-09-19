# 🧙‍♂️ Guía de Clase: Backend Mágico con Spring Boot y Jakarta

Esta guía contiene la estructura y los ejemplos a presentar en las diapositivas de la clase.

---

## Diapositiva 1: Bienvenidos al Nivel Avanzado
**¿Qué es Spring Boot?**
* Es el motor mágico que levanta nuestra aplicación.
* Nos libera de configuraciones tediosas (Magia de autoconfiguración).

---

## Diapositiva 2: Las Capas de Nuestra Magia (Arquitectura)
La forma correcta de canalizar la magia en nuestra empresa. Todo tiene un lugar:
* **Models (Entidades / DTOs):** Lo que guardamos y lo que comunicamos.
* **Dao:** El bibliotecario (Acceso a la Base de Datos).
* **Manager:** El director (Lógica de negocio y reglas).
* **Services:** Las puertas del colegio (Nuestras APIs expuestas).

---

## Diapositiva 3: Capa Models (Objetos Mágicos)
¿Qué es una Entidad? Es el reflejo exacto de una tabla en la base de datos.

```java
@Entity
public class HechizoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private Integer nivelPoder;
    
    // Getters y Setters...
}
```

---

## Diapositiva 4: Capa DAO (El Bibliotecario)
Encargada exclusivamente de hablar con la Base de Datos.

```java
@Repository // ¡Fundamental!
public class HechizoDaoImpl implements HechizoDao {
    
    @PersistenceContext
    private EntityManager entityManager;

    public HechizoEntity guardar(HechizoEntity hechizo) {
        entityManager.persist(hechizo);
        return hechizo;
    }
}
```

---

## Diapositiva 5: Consultas Seguras vs Magia Oscura
❌ **Inyección SQL (Magia Oscura):**
```java
// NUNCA HAGAS ESTO
String sql = "SELECT h FROM HechizoEntity h WHERE h.nombre = " + nombre;
```

✅ **Uso correcto de Query (Magia Blanca):**
```java
Query query = entityManager.createQuery("SELECT h FROM Hechizo h WHERE h.nombre = :nombre");
query.setParameter("nombre", nombreA_buscar);
```

---

## Diapositiva 6: Capa Manager (Lógica de Negocio)
Orquesta a los DAOs y aplica las reglas del colegio.

```java
@Service // ¡El núcleo!
public class HechizoManagerImpl implements HechizoManager {

    @Autowired
    private HechizoDao dao;

    @Transactional // Si algo falla, se deshace todo
    public HechizoEntity registrarHechizo(HechizoEntity hechizo) throws Exception {
        if ("Oscura".equals(hechizo.getTipoMagia())) {
            throw new Exception("Magia prohibida");
        }
        return dao.guardar(hechizo);
    }
}
```

---

## Diapositiva 7: Capa Services (Puertas del Colegio - APIs)
Usamos Jakarta para exponer las URLs hacia el exterior.

```java
@Path("/hechizos") // La ruta de la puerta
@Produces(MediaType.APPLICATION_JSON)
public class HechizoService {

    @Autowired
    private HechizoManager manager;

    @POST // Para crear (Insertar magia)
    public Response crear(HechizoEntity hechizo) {
        manager.registrarHechizo(hechizo);
        return Response.status(201).build();
    }
}
```

---

## Diapositiva 8: Parámetros (Path vs Query)

**@PathParam:** Cuando el parámetro es parte fija de la ruta (Ej: `/hechizos/5`)
```java
@GET
@Path("/{id}")
public Response obtenerPorId(@PathParam("id") Long id) { ... }
```

**@QueryParam:** Cuando el parámetro es opcional o de búsqueda (Ej: `/hechizos?tipo=Fuego`)
```java
@GET
public Response buscarPorTipo(@QueryParam("tipo") String tipo) { ... }
```
