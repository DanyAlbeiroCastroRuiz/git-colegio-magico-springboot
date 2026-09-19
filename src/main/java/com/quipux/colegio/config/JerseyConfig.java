package com.quipux.colegio.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.quipux.colegio.services.HechizoService;

@Component
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        // Registrar el servicio/controlador para exponer la API
        register(HechizoService.class);
    }
}
