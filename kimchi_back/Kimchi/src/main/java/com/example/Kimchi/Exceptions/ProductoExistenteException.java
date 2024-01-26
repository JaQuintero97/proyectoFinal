package com.example.Kimchi.Exceptions;

public class ProductoExistenteException extends Exception {
    public ProductoExistenteException(Long id) {
        super("Ya existe un producto con el ID: " + id);
    }
}
