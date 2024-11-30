package cl.myccontadores.cobros.controller;

import cl.myccontadores.cobros.dto.DetallePagoDTO;
import cl.myccontadores.cobros.dto.PagoDTO;
import cl.myccontadores.cobros.dto.request.CrearPagoRequestDTO;
import cl.myccontadores.cobros.dto.request.CreateDetallePagoRequest;
import cl.myccontadores.cobros.entity.Pago;
import cl.myccontadores.cobros.service.DetallePagoService;
import cl.myccontadores.cobros.service.PagoService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})

public class PagoController {

    @Autowired
    private PagoService pagoService;

    @Autowired
    private DetallePagoService detallePagoService;

    @PostMapping
    public ResponseEntity<PagoDTO> registrarPago(@RequestBody CrearPagoRequestDTO pago) throws BadRequestException {
        PagoDTO nuevoPago = pagoService.registrarPago(pago);
        return ResponseEntity.ok(nuevoPago);
    }


    @PostMapping("/{pagoId}/detalles")
    public ResponseEntity<DetallePagoDTO> agregarDetallePago(
            @PathVariable("pagoId") Long pagoId,
            @RequestBody CreateDetallePagoRequest request) {
        request.setPagoId(pagoId);
        return ResponseEntity.ok(detallePagoService.agregarDetallePago(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoDTO> obtenerPagoPorId(@PathVariable Long id) {
        PagoDTO pago = pagoService.findById(id);
        return ResponseEntity.ok(pago);
    }

//    @GetMapping("/cliente/{clienteId}")
//    public List<PagoDTO> obtenerPagosPorCliente(@PathVariable Long clienteId) {
//        return pagoService.obtenerPagosPorClienteId(clienteId);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPago(@PathVariable Long id) {
        pagoService.deletePago(id);
        return ResponseEntity.noContent().build();
    }
}
