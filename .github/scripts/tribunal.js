const fs = require('fs');
const path = require('path');

const RETOS = [
    {
        title: "Reto 1: El Objeto Mágico (Capa Models) ⚔️",
        body: "¡Bienvenido al Nivel Avanzado del Colegio Mágico!\n\nTu primera misión es preparar nuestra base de datos. En Spring Boot no creamos las tablas con scripts SQL a mano, usamos código Java.\n\n**Misión:**\n1. Ve a `src/main/java/com/quipux/colegio/models/HechizoEntity.java`.\n2. Completa las anotaciones faltantes (`@Entity`, `@Table(name=\"hechizos\")`, `@Id`, `@GeneratedValue(strategy=GenerationType.IDENTITY)` y `@Column(name=\"nombre\", nullable=false)`).\n3. Haz `git add`, `git commit` y `git push`.\n\n¡Estaremos evaluando tu magia automáticamente! ✨",
        testFile: "com.quipux.colegio.models.Reto1EntityTest.txt",
        hint: "💡 **Comparativa PHP:** En PHP usarías clases normales y si usas ORM como Eloquent asumes nombres de tabla. Aquí en Spring le decimos TODO a Hibernate con anotaciones como `@Entity` y `@Column`. Recuerda que `@Id` es obligatorio para la llave primaria."
    },
    {
        title: "Reto 2: El Bibliotecario (Capa DAO) 📚",
        body: "¡Excelente! Has superado el primer reto.\n\nEl Bibliotecario necesita guardar los hechizos de forma segura.\n\n**Misión:**\n1. Ve a `src/main/java/com/quipux/colegio/dao/HechizoDaoImpl.java`.\n2. Asegúrate de marcar esta clase como un Repositorio en Spring (`@R...`).\n3. Completa `guardarHechizo` usando `entityManager.persist(hechizo)`.\n4. Completa las consultas creando un objeto `Query` seguro (usando `setParameter()`).\n5. Haz commit y push.",
        testFile: "com.quipux.colegio.dao.Reto2DaoTest.txt",
        hint: "💡 **Comparativa PHP:** En lugar de `PDO` y `$stmt->execute()`, aquí usas `entityManager.createQuery()` y `setParameter()` para evitar Inyección SQL. Para insertar no escribes `INSERT INTO`, solo usas `entityManager.persist(hechizo)`."
    },
    {
        title: "Reto 3: Las Reglas Mágicas (Capa Manager) 🧠",
        body: "¡Increíble! Ahora entramos al cerebro del colegio.\n\n**Misión:**\n1. Ve a `src/main/java/com/quipux/colegio/manager/HechizoManagerImpl.java`.\n2. Configúrala como un servicio transaccional (`@S...`, `@T...`).\n3. En `registrarHechizo`, verifica que el nombre no sea nulo ni esté vacío (lanza Exception \"Nombre invalido\").\n4. Si la magia es \"Oscura\", lanza Exception `\"Magia prohibida en el colegio\"`.\n\n⚠️ **RECUERDA LAS REGLAS DE ORO:**\n- **No modifiques las pruebas (`Test.java`)**.",
        testFile: "com.quipux.colegio.manager.Reto3ManagerTest.txt",
        hint: "💡 **Comparativa PHP:** Aquí iría la lógica que normalmente pones antes de guardar (validar strings vacíos). Usa `@Service` para registrar la clase y `@Transactional` para que Spring haga un `ROLLBACK` automático en BD si lanzas una Exception."
    },
    {
        title: "Reto 4: Las Puertas del Colegio (Capa Services) 🚪",
        body: "¡Casi lo logras! Solo falta abrir las puertas para que el mundo vea nuestra magia.\n\n**Misión:**\n1. Ve a `src/main/java/com/quipux/colegio/services/HechizoService.java`.\n2. Configura la ruta a `/hechizos` (`@P...`).\n3. Configura `crearHechizo` con `@POST`. Si el manager lanza Exception, captura y retorna `Response.status(Response.Status.BAD_REQUEST)`.\n4. Configura `buscarPorTipo` con `@GET` y un `@QueryParam(\"tipo\")`.",
        testFile: "com.quipux.colegio.services.Reto4ServiceIntegrationTest.txt",
        hint: "💡 **Comparativa PHP:** En lugar de leer `$_POST['nombre']` o `$_GET['tipo']`, `@POST` convierte automáticamente el JSON en el objeto `HechizoEntity`, y `@QueryParam` atrapa las variables de la URL. Si hay un error de validación, devolvemos Status 400 (Bad Request)."
    },
    {
        title: "Reto 5: Graduación Backend 🎓 (Pull Request Final)",
        body: "🎉 **¡FELICIDADES GRAN MAGO DE BACKEND!** 🎉\n\nHas superado todos los retos de código y dominado las capas de Spring Boot y Jakarta.\n\n**Misión Final:**\n1. En tu terminal, crea una nueva rama y cámbiate a ella: `git checkout -b graduacion-backend`.\n2. Copia el archivo `PLANTILLA_ENTREGA.md` y guárdalo dentro de la carpeta `magos_graduados` con tu nombre (ej: `magos_graduados/JUAN_PEREZ.md`). ¡Responde allí a las preguntas de reflexión!\n3. Sube tu archivo a esta nueva rama: `git add .`, `git commit -m \"feat: graduacion completada\"` y `git push origin graduacion-backend`.\n4. Ve a tu repositorio en GitHub y abre un **Pull Request** hacia el repositorio original del colegio.\n\n¡Al hacer el Pull Request habrás terminado oficialmente tu especialización en Backend! 🎓\n\n**--CALIFICACION_AQUI--**",
        testFile: null
    }
];

module.exports = async ({github, context}) => {
    const owner = context.repo.owner;
    const repo = context.repo.repo;

    console.log("Consultando el Tribunal Mágico (Backend)...");

    const { data: issues } = await github.rest.issues.listForRepo({
        owner,
        repo,
        state: 'open',
        creator: 'github-actions[bot]'
    });

    const openIssues = issues.filter(issue => issue.title.startsWith('Reto'));

    if (openIssues.length === 0) {
        const { data: closedIssues } = await github.rest.issues.listForRepo({ owner, repo, state: 'closed' });
        if (closedIssues.length === 0) {
            console.log("Inicio de aventura. Evaluando Reto 1.");
            const testReportPath = path.join('target', 'surefire-reports', RETOS[0].testFile);
            let testPassed = false;
            
            if (fs.existsSync(testReportPath)) {
                const reportContent = fs.readFileSync(testReportPath, 'utf8');
                if (reportContent.includes('Failures: 0') && reportContent.includes('Errors: 0')) {
                    testPassed = true;
                }
            }
            
            if (testPassed) {
                await github.rest.issues.create({ 
                    owner, repo, 
                    title: RETOS[1].title, 
                    body: "✅ **¡Has despertado al Tribunal Mágico con éxito y tu Entidad está perfecta!**\n\n---\n\n" + RETOS[1].body 
                });
            } else {
                await github.rest.issues.create({ 
                    owner, repo, 
                    title: RETOS[0].title, 
                    body: "❌ **¡Has despertado al Tribunal, pero notamos fallas en la Entidad!**\n\n---\n\n" + RETOS[0].body 
                });
            }
            return;
        } else {
            console.log("Todos los retos completados o no hay issues activos.");
            return;
        }
    }

    const currentIssue = openIssues[0];
    const match = currentIssue.title.match(/Reto (\d+)/);
    if (!match) return;
    
    const retoIndex = parseInt(match[1]) - 1;
    const reto = RETOS[retoIndex];

    console.log(`Evaluando el ${reto.title}...`);

    if (retoIndex === 4) { // Índice 4 es el Reto 5
        console.log("Esperando graduación manual.");
        return;
    }

    const { data: currentComments } = await github.rest.issues.listComments({
        owner, repo, issue_number: currentIssue.number
    });
    
    const failsInCurrentIssue = currentComments.filter(c => c.body.includes('❌ **El Tribunal ha detectado fallas')).length;

    const testReportPath = path.join('target', 'surefire-reports', reto.testFile);
    let testPassed = false;
    
    if (fs.existsSync(testReportPath)) {
        const reportContent = fs.readFileSync(testReportPath, 'utf8');
        if (reportContent.includes('Failures: 0') && reportContent.includes('Errors: 0')) {
            testPassed = true;
        }
    }

    if (testPassed) {
        console.log("¡Hechizo exitoso!");
        const puntosObtenidos = Math.max(2, 10 - (2 * failsInCurrentIssue));
        const notaReto = (puntosObtenidos / 10) * 5.0;
        
        await github.rest.issues.createComment({
            owner, repo,
            issue_number: currentIssue.number,
            body: `✅ **¡Hechizo superado!**\n\nEl Tribunal Mágico ha verificado tu código y funciona perfectamente.\n\n⭐ **Puntos obtenidos en este reto:** ${puntosObtenidos}/10 (Nota: ${notaReto.toFixed(1)})\n\nVoy a cerrar este reto y abriré el siguiente.`
        });
        
        await github.rest.issues.update({
            owner, repo,
            issue_number: currentIssue.number,
            state: 'closed'
        });
        
        const nextReto = RETOS[retoIndex + 1];
        if (nextReto) {
            let nextBody = nextReto.body;
            if (retoIndex + 1 === 4) { // Índice 4 es el reto 5 final
                let puntajeTotal = 10;
                const { data: allIssues } = await github.rest.issues.listForRepo({ owner, repo, state: 'all', creator: 'github-actions[bot]' });
                
                for (let i = 0; i < 4; i++) {
                    const issueAnt = allIssues.find(iss => iss.title.startsWith(`Reto ${i+1}:`));
                    if (issueAnt) {
                        const { data: comms } = await github.rest.issues.listComments({ owner, repo, issue_number: issueAnt.number });
                        const fCount = comms.filter(c => c.body.includes('❌ **El Tribunal ha detectado fallas')).length;
                        puntajeTotal += Math.max(2, 10 - (2 * fCount));
                    } else {
                        puntajeTotal += 10;
                    }
                }
                
                const nota = (puntajeTotal / 50) * 5.0;
                const calificacionText = `### 🏆 Tu Calificación Final\n\n**Puntuación Total:** ${puntajeTotal}/50\n**Nota Final:** ${nota.toFixed(1)}`;
                nextBody = nextBody.replace("**--CALIFICACION_AQUI--**", calificacionText);
            }
            
            await github.rest.issues.create({ owner, repo, title: nextReto.title, body: nextBody });
        }
    } else {
        console.log("El hechizo falló.");
        let pistaTexto = "";
        if (failsInCurrentIssue >= 1 && reto.hint) {
            pistaTexto = `\n\n${reto.hint}`;
        }

        await github.rest.issues.createComment({
            owner, repo,
            issue_number: currentIssue.number,
            body: `❌ **El Tribunal ha detectado fallas en tu hechizo.**\n\nAsegúrate de haber completado el código correctamente y de que pase las pruebas locales usando \`mvn test\` antes de volver a hacer push.${pistaTexto}\n\n¡No te rindas, sigue practicando! 🪄`
        });
    }
};
