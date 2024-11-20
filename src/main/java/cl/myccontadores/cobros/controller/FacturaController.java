package cl.myccontadores.cobros.controller;

import cl.myccontadores.cobros.dto.DetalleFacturaDTO;
import cl.myccontadores.cobros.dto.FacturaDTO;
import cl.myccontadores.cobros.entity.Factura;
import cl.myccontadores.cobros.enums.EstadoFactura;
import cl.myccontadores.cobros.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})

public class FacturaController {
    @Autowired
    private FacturaService facturaService;

    @PostMapping
    public ResponseEntity<FacturaDTO> crearFactura(@RequestBody FacturaDTO factura) {
        FacturaDTO nuevaFactura = facturaService.crearFactura(factura);
        return ResponseEntity.ok(nuevaFactura);
    }
    @PostMapping("/detalles")
    public ResponseEntity<Factura> agregarDetalleFactura(
            @RequestBody DetalleFacturaDTO detalleDTO) {
        Factura facturaActualizada = facturaService.agregarDetalleFactura(detalleDTO);
        return ResponseEntity.ok(facturaActualizada);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Factura> obtenerFacturaPorId(@PathVariable Long id) {
        Factura factura = facturaService.obtenerFacturaPorId(id);
        return ResponseEntity.ok(factura);
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Factura> obtenerFacturasPorCliente(@PathVariable Long clienteId) {
        return facturaService.obtenerFacturasPorClienteId(clienteId);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Factura> actualizarEstadoFactura(@PathVariable Long id, @RequestParam EstadoFactura estado) {
        Factura facturaActualizada = facturaService.actualizarEstadoFactura(id, estado);
        return ResponseEntity.ok(facturaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFactura(@PathVariable Long id) {
        facturaService.eliminarFactura(id);
        return ResponseEntity.noContent().build();
    }
}
