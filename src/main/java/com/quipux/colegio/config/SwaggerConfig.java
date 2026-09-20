package com.quipux.colegio.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
// Explicación de la solución:
// Usamos url = "/" para que todas las peticiones desde la página web de Swagger se hagan de forma "relativa"
// a la URL en la que estamos navegando.
// Así funcionará perfecto tanto en "http://localhost:8080" como en el dominio autogenerado de Github Codespaces 
// (ej: "https://xyz-8080.app.github.dev"). ¡Esto evitará el molesto error 404!
@OpenAPIDefinition(
    info = @Info(title = "Colegio Mágico API", version = "1.0", description = "API del Semillero (Ejemplo Mago)"),
    servers = { @Server(url = "/", description = "Servidor Actual (Soporta Github Codespaces)") }
)
public class SwaggerConfig {
}
