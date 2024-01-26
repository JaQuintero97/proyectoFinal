package com.example.Kimchi.Controller;
import com.example.Kimchi.Entities.Producto;
import com.example.Kimchi.Exceptions.ProductoInvalidoException;
import com.example.Kimchi.Exceptions.ProductoNotFoundException;
import com.example.Kimchi.Service.ArchivoService;
import com.example.Kimchi.Service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productos")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @Autowired
    private ArchivoService archivoService;

    // Crear Producto
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> agregarProducto(@RequestParam("nombre") String nombre,
                                             @RequestParam("precio") Double precio,
                                             @RequestParam("imagen") MultipartFile imagen) {
        Producto producto = new Producto(nombre, precio);
            if (!imagen.isEmpty()) {
                String archivo = producto.getNombre() + getExtension(imagen.getOriginalFilename());
                producto.setImagen(archivo);
                try {
                    archivoService.guardar(archivo, imagen.getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        try {
            Optional<Producto> productoRegistradoOptional = productoService.registrarProducto(producto);

            if (productoRegistradoOptional.isPresent()) {
                Producto productoRegistrado = productoRegistradoOptional.get();
                return ResponseEntity.status(HttpStatus.CREATED).body(productoRegistrado);
            } else {
                return ResponseEntity.badRequest().body("No se pudo registrar el producto correctamente");
            }
        } catch (ProductoInvalidoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

    private  String getExtension(String archivo){
        return archivo.substring(archivo.lastIndexOf("."));
    }

    //Listar todos los productos
    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos() {
        List<Producto> productos = productoService.obtenerTodos();
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    //Listar un producto
    @GetMapping("/{nombre}")
    public ResponseEntity<List<Producto>> listarProductoNombre(@PathVariable String nombre) {
        try {
            List<Producto> productos = productoService.obtenerProductoPorNombre(nombre);
            return new ResponseEntity<>(productos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Actualizar un producto
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        try {
            Producto productoActualizado = productoService.actualizarProducto(id, producto);
            return new ResponseEntity<>(productoActualizado, HttpStatus.OK);
        } catch (ProductoNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Eliminar un producto
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable Long id) {
        try {
            String mensaje = productoService.eliminarProducto(id);
            return new ResponseEntity<>(mensaje, HttpStatus.OK);
        } catch (ProductoNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}


