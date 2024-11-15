package cl.myccontadores.cobros.dto;

import cl.myccontadores.cobros.entity.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagoDTO implements Serializable {

    private Long id;
    private ClienteDTO cliente;
    private FacturaDTO factura;
    private LocalDateTime fechaPago;
    private Integer monto;
    private MetodoPagoDTO metodoPago;
    private ComprobanteDTO comprobante;

    public PagoDTO (Pago model){
        this.id = model.getId();
        this.cliente = Optional.ofNullable(model.getCliente()).map(ClienteDTO::new).orElse(null);
        this.factura = Optional.ofNullable(model.getFactura()).map(FacturaDTO::new).orElse(null);
        this.fechaPago = model.getFechaPago();
        this.monto = model.getMonto();
        this.metodoPago = Optional.ofNullable(model.getMetodoPago()).map(MetodoPagoDTO::new).orElse(null);
        this.comprobante = Optional.ofNullable(model.getComprobante()).map(ComprobanteDTO::new).orElse(null);
    }
}
