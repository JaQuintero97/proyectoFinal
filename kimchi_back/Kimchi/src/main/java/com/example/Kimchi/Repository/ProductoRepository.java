package com.example.Kimchi.Repository;
import com.example.Kimchi.Entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto,Long> {
    @Query("FROM Producto p WHERE LOWER(p.nombre) LIKE LOWER(concat('%', :nombre, '%'))")
    List<Producto> obtenerProductoPorNombre(@Param("nombre") String nombre);
}
