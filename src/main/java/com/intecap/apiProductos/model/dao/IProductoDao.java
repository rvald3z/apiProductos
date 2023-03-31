package com.intecap.apiProductos.model.dao;

import com.intecap.apiProductos.model.Producto;
import org.springframework.data.repository.CrudRepository;
public interface  IProductoDao extends CrudRepository <Producto, Long> {
}
