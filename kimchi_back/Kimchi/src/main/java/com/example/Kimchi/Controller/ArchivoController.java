package com.example.Kimchi.Controller;

import com.example.Kimchi.Service.ArchivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ArchivoController {

    @Autowired
    ArchivoService archivoService;

    @GetMapping("/archivos/{nombre}")
    public ResponseEntity<Resource> get(@PathVariable String nombre) {
        return archivoService.get(nombre);
    }
}
