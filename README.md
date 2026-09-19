# 🧙‍♂️ Colegio Mágico - Magia Avanzada (Backend)

¡Bienvenido al nivel avanzado de tu formación mágica! El Ministerio de Magia ha ordenado digitalizar el *Registro de Hechizos* del colegio. Como aprendiz, debes construir la API REST que controlará las Puertas del Colegio, siguiendo la sagrada Arquitectura por Capas.

## 📜 Instrucciones para Empezar

1. **Haz un Fork** de este repositorio hacia tu cuenta personal.
2. **Crea tu entorno mágico (Codespace):** 
   - No necesitas clonarlo en local. Simplemente ve a tu repositorio *forkeado* en GitHub.
   - En la URL, cambia `github.com` por `github.dev` (o simplemente presiona la tecla `.` en tu teclado) para abrir el editor web.
   - Alternativamente, haz clic en el botón verde **Code**, ve a la pestaña **Codespaces** y haz clic en **Create codespace on main** para tener una terminal integrada.
3. Activa la "Magia del Tribunal" (GitHub Actions) en la pestaña **Actions** de tu repositorio.
4. **Reto 0 (Clase con el Profesor):** El profesor te guiará en vivo construyendo la primera entidad (`Mago`). Presta mucha atención, ya que aplicarás los mismos conceptos. Si te pierdes, la guía está en [GUIA_PROFESOR.md](./GUIA_PROFESOR.md).
5. Completa los Retos 1 al 4 siguiendo las pistas en el código. ¡Revisa la carpeta `assets/` si necesitas ayuda con los hechizos de Git!
   - [Cómo usar tu varita Git (Comandos Básicos)](./assets/varita_git.md)
   - [El Grimorio de Commits](./assets/grimorio_commits.md)
   - [Guía de Pull Requests](./assets/guia_pull_request.md)
6. Ejecuta las pruebas en la terminal de tu Codespace usando `mvn test` para validar tus respuestas antes de hacer push.
7. **Prueba tu magia:** Puedes iniciar el servidor en el Codespace (`mvn spring-boot:run`) y probar tus hechizos ingresando a `/swagger-ui.html` en el puerto reenviado.
8. Haz `git add`, `git commit`, y `git push`. ¡El Gran Tribunal de GitHub calificará tus hechizos!

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
