package cl.myccontadores.cobros.controller;

import cl.myccontadores.cobros.entity.Comprobante;
import cl.myccontadores.cobros.entity.FileDB;
import cl.myccontadores.cobros.enums.TipoComprobante;
import cl.myccontadores.cobros.service.ComprobanteService;
import cl.myccontadores.cobros.service.FileStorageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/comprobantes")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Log4j2
public class ComprobanteController {
    @Autowired
    private ComprobanteService comprobanteService;

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping
    ResponseEntity<Comprobante> comprobanteResponseEntity(
            @RequestParam("file") MultipartFile file,
            @RequestParam("tipo") TipoComprobante tipo) {
        try {
            FileDB fileDB = fileStorageService.store(file);

            Comprobante comprobante = Comprobante.builder()
                    .tipo(tipo)
                    .fecha(LocalDateTime.now())
                    .file(fileDB)
                    .build();

            Comprobante nuevoComprobante = comprobanteService.crearComprobante(comprobante);
            return ResponseEntity.ok(nuevoComprobante);
        } catch (IOException e) {
            log.error("Error al subir el archivo", e);
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comprobante> obtenerComprobantePorId(@PathVariable Long id) {
        Comprobante comprobante = comprobanteService.obtenerComprobantePorId(id);
        return ResponseEntity.ok(comprobante);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarComprobante(@PathVariable Long id) {
        comprobanteService.borrarComprobante(id);
        return ResponseEntity.noContent().build();
    }

}
