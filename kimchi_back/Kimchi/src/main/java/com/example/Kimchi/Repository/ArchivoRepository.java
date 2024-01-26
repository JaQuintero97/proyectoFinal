package com.example.Kimchi.Repository;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.io.InputStream;

@Repository
public interface ArchivoRepository {
    public void guardar(String archivo, InputStream bytes);
    public void eliminar(String archivo);
    public ResponseEntity<Resource> get(String archivo);
}
