package com.intecap.apiProductos.model.dao;
import com.intecap.apiProductos.model.Fabricante;
import org.springframework.data.repository.CrudRepository;

public interface IFabricanteDao extends CrudRepository <Fabricante, Long> {
}