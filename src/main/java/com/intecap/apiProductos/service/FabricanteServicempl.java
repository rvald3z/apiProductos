package com.intecap.apiProductos.service;

import com.intecap.apiProductos.model.Fabricante;
import com.intecap.apiProductos.model.dao.IFabricanteDao;
import com.intecap.apiProductos.response.FabricanteResponseRest;
import com.intecap.apiProductos.response.ResponseRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


@Service
public class FabricanteServicempl implements IFabricanteService {

    private static final Logger log = Logger.getLogger(FabricanteServicempl.class.getName());

    @Autowired
    private IFabricanteDao fabricanteDao;

    //Listando Fabricnate
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<FabricanteResponseRest> buscarFabricante() {
        log.info("Inicio metodo buscarFabricante");
        FabricanteResponseRest response = new FabricanteResponseRest();

        try {
            List<Fabricante> listFabricante = (List<Fabricante>) fabricanteDao.findAll();
            response.getFabricanteResponse().setFabricantes(listFabricante);
            response.setMetadata("Respuesta OK", "200", "Lista de categorias"); //llama al metodo setMetadata de la clase CategoriaResponseRest para enviar el mensaje de respuesta al cliente

        } catch (Exception e) {
            log.info("Error al consultar la informacion del fabricante");
            e.getStackTrace();
            response.setMetadata("Respuesta No Ok", "-1", "Respuesta Incorrecta");
            return new ResponseEntity<FabricanteResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return new ResponseEntity<FabricanteResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Buscando un Fabricante en Especifico
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<FabricanteResponseRest> buscarFabricanteId(Long id) {
        log.info("Inicio Buscando Fabricante por ID");
        FabricanteResponseRest response = new FabricanteResponseRest();
        List<Fabricante> listFabricanteId = new ArrayList<>();
        try {
            Optional<Fabricante> fabricante = fabricanteDao.findById(id);
            if (fabricante.isPresent()) {
                listFabricanteId.add(fabricante.get());
                response.getFabricanteResponse().setFabricantes(listFabricanteId);
                response.setMetadata("Repuesta OK", "200", "Fabricante Encontrado");
            } else {
                response.setMetadata("Respuesta No OK", "-1", "Fabricante No encontrado");
                return new ResponseEntity<FabricanteResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.info("Error al consultar el Fabricante");
            e.getStackTrace();
            response.setMetadata("Respuesta Ok", "-1", "Errar al consultar el Fabricante");
            return new ResponseEntity<FabricanteResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<FabricanteResponseRest>(response, HttpStatus.OK);
    }

    //Creando Fabricante
    @Override
    @Transactional
    public ResponseEntity<FabricanteResponseRest> creandoFabricante(Fabricante fabricante) {
        log.info("Iniciando el metodo crear");
        FabricanteResponseRest response = new FabricanteResponseRest();
        List<Fabricante> list = new ArrayList<>();

        try{
            Fabricante fabricanteGuardado = fabricanteDao.save(fabricante);
            if(fabricanteGuardado != null) {
                list.add(fabricanteGuardado);
                response.getFabricanteResponse().setFabricantes(list);
            }else{
                log.severe("Error al guardar Fabricante");
                response.setMetadata("Error al guardar Fabricante", "500", "Error al Guardar FABRICANTE");
                return new ResponseEntity<FabricanteResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }catch (Exception e){

            log.severe("Error al guardar Fabricante");
            e.getStackTrace();
            response.setMetadata("Error al guardar Fabricante", "500", "Error al Guardar FABRICANTE");
            return new ResponseEntity<FabricanteResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.setMetadata("Respuesta Exitosa", "200", "Fabricante Creado");
        return new ResponseEntity<FabricanteResponseRest>(response, HttpStatus.OK);
    }

    //Actualizando un producto en especifico
    @Override
    @Transactional
    public ResponseEntity<FabricanteResponseRest> actualizarFabricante(Long id, Fabricante fabricante) {
        log.info("Iniciando el proceso de Actualizacion");

        FabricanteResponseRest response = new FabricanteResponseRest();
        List<Fabricante> listFabricante = new ArrayList<>();

        try {
            Optional<Fabricante> fabricanteBuscado = fabricanteDao.findById(id);

            if (fabricanteBuscado.isPresent()) {
                //probando si el codigo es que le hace falta
                fabricanteBuscado.get().setCodigo(fabricante.getCodigo());
                fabricanteBuscado.get().setNombre(fabricante.getNombre());
                Fabricante fabricanteActualizado = fabricanteDao.save(fabricanteBuscado.get());

                if (fabricanteActualizado != null) {
                    listFabricante.add(fabricanteActualizado);
                    response.getFabricanteResponse().setFabricantes(listFabricante);
                } else {
                    log.severe("Error al Actualizar el Fabricante");
                    response.setMetadata("Error al actualizar el fabricante", "500", "Error al actualizar el Fabricante");
                    return new ResponseEntity<FabricanteResponseRest>(response, HttpStatus.BAD_REQUEST);
                }
            } else {
                log.severe("No se encontro el fabricante con el id" + id);
                response.setMetadata("No se encontro el Fabricante", "404", "No se encontro el Fabricante con el id " + id);
                return new ResponseEntity<FabricanteResponseRest>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            log.severe("Error al actualizar el Fabricante" + e.getMessage());
            e.getStackTrace();
            response.setMetadata("Repuesta Exitosa", "200", "Fabricante Actualizado");
            return new ResponseEntity<FabricanteResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.setMetadata("Respuesta exitosa", "200", "Fabricante Actualizado");
        return new ResponseEntity<FabricanteResponseRest>(response, HttpStatus.OK);
    }

    //Eliminando un Producto en Especifico
    @Override
    public ResponseEntity<FabricanteResponseRest> eliminarFabricante(Long id) {
        log.info("Iniciando el proceso de Elimnar Fabricante");
        FabricanteResponseRest response = new FabricanteResponseRest();

        try {
            Optional<Fabricante> fabricanteBuscado = fabricanteDao.findById(id);

            if (fabricanteBuscado.isPresent()) {
                fabricanteDao.delete(fabricanteBuscado.get());
            } else {
                log.severe("No se encontro el fabricante con el id: " + id);
                response.setMetadata("No se encontro el Fabricante", "404", "No se encontro el Fabricante con el id: " + id);
                return new ResponseEntity<FabricanteResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.severe("Error al eliminar el libro" + e.getMessage());
            e.getStackTrace();
            response.setMetadata("Error al elimnar el fabricante", "500", "Error al eliminar el fabricante");
        }

        response.setMetadata("Respuesta exitosa", "200", "Fabricante Eliminado");
        return new ResponseEntity<FabricanteResponseRest>(response, HttpStatus.OK);
    }

}