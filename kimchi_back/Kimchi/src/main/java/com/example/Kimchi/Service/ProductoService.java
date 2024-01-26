package com.example.Kimchi.Service;
import com.example.Kimchi.Entities.Producto;
import com.example.Kimchi.Exceptions.ProductoInvalidoException;
import com.example.Kimchi.Exceptions.ProductoNotFoundException;
import com.example.Kimchi.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public Optional<Producto> registrarProducto(Producto producto) throws ProductoInvalidoException {
        if (producto.getNombre() == null || producto.getNombre().isEmpty()) {
            throw new ProductoInvalidoException("El nombre del producto no puede ser nulo o vacío");
        }
        if (producto.getPrecio() == null || producto.getPrecio().isNaN() || producto.getPrecio() <= 0) {
            throw new ProductoInvalidoException("El precio del producto no puede ser nulo y debe ser un numero");
        }
        return Optional.of(productoRepository.save(producto));
    }

    public List<Producto> obtenerProductoPorNombre(String nombre)  {
          return productoRepository.obtenerProductoPorNombre(nombre);
    }

   public List<Producto> obtenerTodos(){
        return productoRepository.findAll();
   }

    public Producto actualizarProducto(Long id, Producto producto) throws ProductoNotFoundException {
        // Verificar si el producto existe en la base de datos
        Optional<Producto> productoExistente = productoRepository.findById(id);
        if (productoExistente.isPresent()) {
            Producto productoExistenteActualizado = productoExistente.get();
            productoExistenteActualizado.setNombre(producto.getNombre());
            productoExistenteActualizado.setPrecio(producto.getPrecio());
            // Guardar el producto actualizado en la base de datos
            return productoRepository.save(productoExistenteActualizado);
        } else {
            // Si no existe, lanzar una excepción
            throw new ProductoNotFoundException(id);
        }
    }

    public String eliminarProducto(Long id) throws ProductoNotFoundException {
        Optional<Producto> productoExistente = productoRepository.findById(id);
        if (productoExistente.isPresent()){
            productoRepository.deleteById(id);
            return "Producto eliminado con éxito";
        } else {
            throw new ProductoNotFoundException(id);
        }
    }

}


