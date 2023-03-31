package com.intecap.apiProductos.service;

import com.intecap.apiProductos.model.Producto;
import com.intecap.apiProductos.model.dao.IProductoDao;
import com.intecap.apiProductos.response.FabricanteResponseRest;
import com.intecap.apiProductos.response.ProductoResponseRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class ProductoServicempl implements IProductoService {

    private static final Logger log = Logger.getLogger(ProductoServicempl.class.getName());

    @Autowired
    private IProductoDao productoDao;

    //Mostrando Listado de Producto
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ProductoResponseRest> buscarProducto() {
        log.info("Buscnado todos los productos");
        ProductoResponseRest response = new ProductoResponseRest();

        try {
            List<Producto> listProducto = (List<Producto>) productoDao.findAll();
            response.getProductoResponse().setProductos(listProducto);
            response.setMetadata("Respuesta Exitosa", "200", "Lista de Productos");
        } catch (Exception e) {
            log.severe("Error al buscar los productos: " + e.getMessage());
            e.getStackTrace();
            response.setMetadata("Error al buscar los libros", "500", e.getMessage());
            return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.OK);
    }

    //Buscando Producto por Id
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ProductoResponseRest> buscarProductoId(Long id) {
        log.info("Inicio Buscando Producto por ID");
        ProductoResponseRest response = new ProductoResponseRest();
        List<Producto> listProductoId = new ArrayList<>();
        try {
            Optional<Producto> producto = productoDao.findById(id);
            if (producto.isPresent()) {
                listProductoId.add(producto.get());
                response.getProductoResponse().setProductos(listProductoId);
                response.setMetadata("Repuesta OK", "200", "Producto Encontrado");
            } else {
                response.setMetadata("Respuesta No OK", "-1", "Producto No encontrado");
                return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.info("Error al consultar el Producto");
            e.getStackTrace();
            response.setMetadata("Respuesta Ok", "-1", "Errar al consultar el Producto");
            return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.OK);

    }

    //Creando Producto
    @Override
    public ResponseEntity<ProductoResponseRest> creandoProducto(Producto producto) {
        log.info("Iniciando el metodo crear Producto");
        ProductoResponseRest response = new ProductoResponseRest();
        List<Producto> list = new ArrayList<>();

        try {
            Producto productoGuardado = productoDao.save(producto);
            if (productoGuardado != null) {
                list.add(productoGuardado);
                response.getProductoResponse().setProductos(list);

            } else {
                log.severe("Error al guardar Producto");
                response.setMetadata("Error al guardar Producto", "500", "Error al Guardar PRODUCTO");
                return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            log.severe("Error al guardar Producto");
            e.getStackTrace();
            response.setMetadata("Error al guardar Producto", "500", "Error al Guardar FABRICANTE");
            return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.setMetadata("Repuesta Exitosa", "200", "Producto Creado");
        return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.OK);
    }

    //Actualizando Producto
    @Override
    @Transactional
    public ResponseEntity<ProductoResponseRest> actualizarProducto(Producto producto, long id) {
        log.info("Iniciando proceso de Actualizar");

        ProductoResponseRest response = new ProductoResponseRest();
        List<Producto> listProducto = new ArrayList<>();

        try {
            Optional<Producto> productoBuscado = productoDao.findById(id);
            if (productoBuscado.isPresent()) {
                //actualizacion de los campos de la tabla producto
                productoBuscado.get().setNombre(producto.getNombre());
                productoBuscado.get().setPrecio(producto.getPrecio());
                //Aqui se actualiza la llavae foranea
                productoBuscado.get().setFabricante(producto.getFabricante());

                Producto productoActualizado = productoDao.save(productoBuscado.get());

                if (productoActualizado != null) {
                    listProducto.add(productoActualizado);
                    response.getProductoResponse().setProductos(listProducto);
                } else {
                    log.severe("Error al actualizar el Producto");
                    response.setMetadata("Error al Actualizar el Producto", "500", "Erro al Actualizar el Producto");
                    return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                }

            } else {
                log.severe("No se encontro el Producto con el id: " + id);
                response.setMetadata("No se encontro el Producto", "404", "No se encontro el Producto para Actualizarlo");
            }

        } catch (Exception e) {
            log.severe("Error al actualizar el Producto. " + e.getMessage());
            e.getStackTrace();
            response.setMetadata("Error al Actualizar el Producto", "500", "Error al Actualizar el Producto");
        }

        return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.OK);
    }

    //Elimiando Producto
    @Override
    public ResponseEntity<ProductoResponseRest> eliminarProducto(Long id) {
        log.info("Iniciando el proceso de Eliminar el Producto");
        ProductoResponseRest response = new ProductoResponseRest();

        try {
            Optional<Producto> fabricanteEliminado = productoDao.findById(id);
            if (fabricanteEliminado.isPresent()) {
                productoDao.delete(fabricanteEliminado.get());
            } else {
                log.severe("No se encontro el Producto con el id: " + id);
                response.setMetadata("No se encontrop el Producto", "404", "No se encontro el Producto con el id " + id);
                return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.severe("Error al eliminar el Producto" + e.getMessage());
            e.getStackTrace();
            response.setMetadata("Error al eliminar el Producto", "500", "Error al eliminar el Producto");

        }
        response.setMetadata("Respuesta Exitosa", "200", "Producto Eliminado");
        return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.OK);
    }


}