package com.quipux.colegio.models;

import jakarta.persistence.*;

// @Entity le dice a Spring que esta clase es un reflejo exacto de una tabla de la Base de Datos.
@Entity
// @Table especifica el nombre exacto de la tabla. ¡Como si fuera la pestaña de un archivo de Excel!
@Table(name = "magos")
public class MagoEntity {

    // @Id indica que esta variable será la Llave Primaria (el identificador único, como la cédula).
    @Id
    // @GeneratedValue le dice a la base de datos que asigne los números en orden automáticamente (1, 2, 3...)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Column relaciona esta variable de Java con la columna "nombre" en la tabla de la base de datos.
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "casa")
    private String casa; // Ej: Gryffindor, Slytherin

    // GETTERS Y SETTERS: Son los métodos obligatorios para poder leer y modificar los datos del mago.
    // Sin ellos, el resto de las capas (Dao, Manager) no podrían sacar ni guardar la información del objeto.
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getCasa() { return casa; }
    public void setCasa(String casa) { this.casa = casa; }
}
