package com.intecap.apiProductos.response;

public class ProductoResponseRest extends ResponseRest {

    private ProductoReponse productoResponse = new ProductoReponse();

    public ProductoReponse getProductoResponse(){
        return productoResponse;
    }

    public void setProductoResponse(ProductoReponse productoResponse){
        this.productoResponse = productoResponse;
    }
}