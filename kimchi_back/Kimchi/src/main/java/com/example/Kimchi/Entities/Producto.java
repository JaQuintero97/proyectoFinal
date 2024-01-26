package com.example.Kimchi.Entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nombre;
    @Column
    private Double precio;

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }


    private String imagen;

    public Producto(String nombre, Double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public Producto(){

    }

    public Producto(String nombre, Double precio, String imagen){
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;
    }

}




