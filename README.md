# 🧙‍♂️ Colegio Mágico - Magia Avanzada (Backend)

¡Bienvenido al nivel avanzado de tu formación mágica! El Ministerio de Magia ha ordenado digitalizar el *Registro de Hechizos* del colegio. Como aprendiz, debes construir la API REST que controlará las Puertas del Colegio, siguiendo la sagrada Arquitectura por Capas.

## 📜 Instrucciones para Empezar

1. **Haz un Fork** de este repositorio hacia tu cuenta personal.
2. **Clona tu repositorio** en tu computadora local:
   ```bash
   git clone https://github.com/TU_USUARIO/colegio-magico.git
   cd colegio-magico
   ```
3. Activa la "Magia del Tribunal" (GitHub Actions) en la pestaña **Actions** de tu repositorio.
4. Completa los retos siguiendo las pistas en el código.
5. Ejecuta las pruebas en tu entorno local usando `mvn test` o desde tu IDE para validar tus respuestas antes de hacer push.
6. **Prueba tu magia:** Puedes iniciar el servidor y probar tus hechizos ingresando a `http://localhost:8080/swagger-ui.html`.
7. Haz `git add`, `git commit`, y `git push`. ¡El Gran Tribunal de GitHub calificará tus hechizos!

---

## 🔮 Los Retos Mágicos

Las puertas de acceso a los retos se encuentran en `src/main/java/com/quipux/colegio/`.

### ⚔️ Reto 1: El Objeto Mágico (Capa Models)
**Misión:** Busca la clase `HechizoEntity` en el paquete `models`.
* Añade la anotación `@Entity` para que sea una entidad de base de datos.
* Añade `@Table(name = "hechizos")` para asignarle la tabla correcta.
* Configura la propiedad `id` como llave primaria autogenerada (`@Id`, `@GeneratedValue(strategy = GenerationType.IDENTITY)`).
* Configura la propiedad `nombre` como una columna obligatoria (`@Column(name = "nombre", nullable = false)`).

### 📚 Reto 2: El Bibliotecario (Capa DAO)
**Misión:** Busca la clase `HechizoDaoImpl` en el paquete `dao`.
* Asegúrate de marcar esta clase como un Repositorio en Spring (`@R...`).
* Completa el método `guardarHechizo` usando `entityManager.persist(hechizo)`.
* Completa las consultas `buscarPorTipo` y `buscarPorNombre` creando un objeto `Query` seguro (usando `setParameter()`) para evitar la magia oscura conocida como *Inyección SQL*.

### 🧠 Reto 3: Las Reglas Mágicas (Capa Manager)
**Misión:** Busca la clase `HechizoManagerImpl` en el paquete `manager`.
* Configúrala como un servicio transaccional (`@S...`, `@T...`).
* En `registrarHechizo`, verifica que el nombre no sea `null` ni esté vacío, lanzando un `Exception("Nombre invalido")`.
* Si el `tipoMagia` es igual a `"Oscura"`, lanza un `Exception("Magia prohibida en el colegio")`.

### 🚪 Reto 4: Las Puertas del Colegio (Capa Services)
**Misión:** Busca la clase `HechizoService` en el paquete `services`.
* Configura la ruta principal de esta puerta a `/hechizos` (`@P...`).
* Configura `crearHechizo` como un método de creación HTTP (`@POST`). Captura excepciones y retorna status HTTP `400 Bad Request`.
* Configura `buscarPorTipo` como un método de lectura HTTP (`@G...`) y asocia su argumento a un parámetro de URL (`@Q...`).

---

## ⚠️ Reglas de Oro
* ¡Prohibido modificar las clases que terminan en `Test.java` en `src/test/`! Si las modificas para hacer trampa, el Tribunal lo sabrá.
* Recuerda los comandos de la varita Git: `git status`, `git add .`, `git commit -m "feat: reto X completado"`, `git push`.
