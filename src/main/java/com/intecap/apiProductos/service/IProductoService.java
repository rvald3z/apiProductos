package com.intecap.apiProductos.service;

import com.intecap.apiProductos.model.Producto;
import com.intecap.apiProductos.response.ProductoResponseRest;
import org.springframework.http.ResponseEntity;

public interface IProductoService {
    //listar todos los productos
    public ResponseEntity<ProductoResponseRest> buscarProducto();

    //Buscando Producto
    public ResponseEntity<ProductoResponseRest> buscarProductoId(Long id);

    //Creando Producto
    public ResponseEntity<ProductoResponseRest> creandoProducto(Producto producto);

    //actualizando Producto
    public ResponseEntity<ProductoResponseRest>actualizarProducto(Producto producto, long id);

    //Eliminar ProductO
    public ResponseEntity<ProductoResponseRest>eliminarProducto(Long id);

}