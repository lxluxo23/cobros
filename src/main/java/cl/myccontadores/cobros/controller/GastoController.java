package cl.myccontadores.cobros.controller;

import cl.myccontadores.cobros.dto.GastoDTO;
import cl.myccontadores.cobros.entity.Gasto;
import cl.myccontadores.cobros.service.GastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/gastos")
public class GastoController {
    @Autowired
    private GastoService gastoService;

    @PostMapping
    public ResponseEntity<Gasto> registrarGasto(@RequestBody GastoDTO gastoDTO) {
        Gasto gasto = Gasto.builder()
                .descripcion(gastoDTO.getDescripcion())
                .monto(gastoDTO.getMonto())
                .fechaGasto(gastoDTO.getFecha() != null ? gastoDTO.getFecha().atStartOfDay() : LocalDateTime.now())
                .build();
        Gasto nuevoGasto = gastoService.registrarGasto(gasto,gastoDTO.getClienteId(),gastoDTO.getComprobanteId());
        return ResponseEntity.ok(nuevoGasto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Gasto> obtenerGastoPorId(@PathVariable Long id) {
        Gasto gasto = gastoService.obtenerGastoPorId(id);
        return ResponseEntity.ok(gasto);
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Gasto> obtenerGastosPorCliente(@PathVariable Long clienteId) {
        return gastoService.obtenerGastosPorClienteId(clienteId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarGasto(@PathVariable Long id) {
        gastoService.eliminarGasto(id);
        return ResponseEntity.noContent().build();
    }
}
