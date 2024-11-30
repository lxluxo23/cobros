package cl.myccontadores.cobros.dto;

import cl.myccontadores.cobros.entity.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagoDTO implements Serializable {

    private Long id;
    private ClienteDTO cliente;
    @JsonBackReference
    private FacturaDTO factura;
    private LocalDateTime fechaPago;
    private Integer monto;
    private MetodoPagoDTO metodoPago;
    private List<DetallePagoDTO> detallesPago;

    public PagoDTO (Pago model){
        this.id = model.getId();
        this.cliente = Optional.ofNullable(model.getCliente()).map(cliente1 -> new ClienteDTO(cliente1.getId())).orElse(null);
        this.factura = Optional.ofNullable(model.getFactura()).map(factura1 -> new FacturaDTO(factura1.getId())).orElse(null);
        this.fechaPago = Optional.ofNullable(model.getFechaPago()).orElse(LocalDateTime.now());
        this.monto = model.getMonto();
        this.metodoPago = Optional.ofNullable(model.getMetodoPago()).map(MetodoPagoDTO::new).orElse(null);
        this.detallesPago = Optional.ofNullable(model.getDetallesPago()).map(detalles -> detalles.stream().map(DetallePagoDTO::new).toList()).orElse(null);
    }
}
