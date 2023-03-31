package com.intecap.apiProductos.controller;

import org.springframework.web.bind.annotation.RestController;
import com.intecap.apiProductos.model.Producto;
import com.intecap.apiProductos.response.ProductoResponseRest;
import com.intecap.apiProductos.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api")
@CrossOrigin(origins = "4000")
public class ProductoRestController {

    @Autowired
    private IProductoService productoService;
    //Mostrando Listado de Fabricantes
    @GetMapping("/productos")
    public ResponseEntity<ProductoResponseRest> buscarProducto(){
        return productoService.buscarProducto();
    }
    //Buscando Fabricante por Id
    @GetMapping("/productos/{id}")
    public ResponseEntity<ProductoResponseRest>buscarProductoId(@PathVariable Long id){
        return productoService.buscarProductoId(id);
    }
    //Creando Producto
    @PostMapping("/productos")
    public ResponseEntity<ProductoResponseRest>creandoProducto(@RequestBody Producto request){
        return productoService.creandoProducto(request);
    }
    //Actualizando Producto
    @PutMapping("/productos/{id}")
    public ResponseEntity<ProductoResponseRest>actualizarProducto(@RequestBody Producto request, @PathVariable Long id){
        return productoService.actualizarProducto(request, id);
    }
    //Elimiando Producto
    @DeleteMapping("/productos/{id}")
    public ResponseEntity<ProductoResponseRest>eliminarProducto(@PathVariable Long id){
        return productoService.eliminarProducto(id);
    }
}