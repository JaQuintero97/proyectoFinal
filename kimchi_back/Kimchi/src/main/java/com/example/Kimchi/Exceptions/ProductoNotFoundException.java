package com.example.Kimchi.Exceptions;

public class ProductoNotFoundException extends Exception {
    public ProductoNotFoundException(Long id) {
        super("No se encontró el producto con ID: " + id);
    }

}
