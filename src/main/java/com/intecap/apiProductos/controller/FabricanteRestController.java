package com.intecap.apiProductos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.intecap.apiProductos.model.Fabricante;
import com.intecap.apiProductos.response.FabricanteResponseRest;
import com.intecap.apiProductos.service.IFabricanteService;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/v1/api")
@CrossOrigin(origins = "4000")
public class FabricanteRestController {
    @Autowired
    private IFabricanteService fabricanteService;
    //Mostrando Listado de Fabricante
    @GetMapping("/fabricante")
    public ResponseEntity<FabricanteResponseRest> buscarFabricante() {
        return fabricanteService.buscarFabricante();
    }
    //Buscando Fabricante por Id
    @GetMapping("/fabricante/{id}")
    public ResponseEntity<FabricanteResponseRest> buscarFabricanteId(@PathVariable Long id) {
        return fabricanteService.buscarFabricanteId(id);
    }
    //Creando Fabricante
    @PostMapping("/fabricante")
    public ResponseEntity<FabricanteResponseRest> creandoFabricante(@RequestBody Fabricante request) {
        return fabricanteService.creandoFabricante(request);
    }
    //Actualizando Fabricante
    @PutMapping("/fabricante/{id}")
    public ResponseEntity<FabricanteResponseRest> actualizarFabricante(@PathVariable Long id, @RequestBody Fabricante request) {
        return fabricanteService.actualizarFabricante(id, request);
    }
    //Eliminado Fabricante
    @DeleteMapping("/fabricante/{id}")
    public ResponseEntity<FabricanteResponseRest> eliminarFabricante(@PathVariable Long id){
        return fabricanteService.eliminarFabricante(id);
    }

}