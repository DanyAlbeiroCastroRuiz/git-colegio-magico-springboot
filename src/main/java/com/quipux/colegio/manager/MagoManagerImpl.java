package com.quipux.colegio.manager;

import com.quipux.colegio.dao.MagoDao;
import com.quipux.colegio.models.MagoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// @Service indica que esta clase es nuestro "Cerebro". Aquí viven nuestras REGLAS DE NEGOCIO.
// ¿Por qué necesitamos un Manager y no llamamos al DAO directamente desde el controlador?
// Porque la Base de Datos es "tonta", ella guarda absolutamente cualquier cosa que le enviemos. 
// El Manager es nuestro "guardia de seguridad" que verifica que los datos sean lógicos, correctos 
// y cumplan con las reglas del colegio (Ej. Que no registren un mago sin nombre) ANTES de enviarlos a la BD.
@Service
// @Transactional asegura que, si hay un error a mitad de camino, los cambios en la base de datos se deshagan (rollback).
@Transactional
public class MagoManagerImpl implements MagoManager {

    // @Autowired trae mágicamente nuestro DAO para poder enviarle los datos cuando estemos seguros de que todo está bien.
    @Autowired
    private MagoDao magoDao;

    @Override
    public void crearMago(MagoEntity mago) throws Exception {
        // REGLA DE NEGOCIO: Validamos que el nombre exista y no sean puros espacios.
        // Si el estudiante intenta enviar un mago anónimo, el sistema se detiene aquí y lanza un error.
        if (mago.getNombre() == null || mago.getNombre().trim().isEmpty()) {
            throw new Exception("El mago debe tener un nombre válido");
        }
        
        // Una vez que el guardia de seguridad (Manager) dice que todo está bien, lo pasamos al DAO para que se guarde.
        magoDao.insert(mago);
    }

    @Override
    public void actualizarMago(Long id, MagoEntity magoActualizado) throws Exception {
        // Antes de actualizar a lo ciego, el Manager debe comprobar que el mago de verdad exista en el sistema.
        MagoEntity magoExistente = magoDao.findById(id);
        
        if (magoExistente == null) {
            throw new Exception("Mago no encontrado para actualizar");
        }
        
        // Le asignamos el ID original para no crear un duplicado sin querer en la base de datos.
        magoActualizado.setId(id);
        magoDao.update(magoActualizado);
    }

    @Override
    public void borrarMago(Long id) throws Exception {
        MagoEntity magoExistente = magoDao.findById(id);
        
        if (magoExistente == null) {
            throw new Exception("Mago no encontrado para eliminar");
        }
        
        magoDao.delete(id);
    }

    // EXCEPCIONES A LA REGLA:
    // A veces, no hay reglas de negocio especiales para simplemente "buscar" algo o mostrar una lista.
    // Simplemente queremos pedirle los datos a la Base de Datos, así que el Manager simplemente llama al DAO "tal cual".
    // ¿Por qué no saltarse al Manager entonces? Porque estaríamos rompiendo la "Arquitectura de Capas" que es sagrada en Spring.
    @Override
    public MagoEntity buscarMagoPorId(Long id) {
        return magoDao.findById(id);
    }

    @Override
    public MagoEntity buscarMagoPorNombre(String nombre) {
        return magoDao.findByName(nombre);
    }

    @Override
    public List<MagoEntity> buscarTodosLosMagos() {
        return magoDao.findAll();
    }
}
