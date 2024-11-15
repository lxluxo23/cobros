package cl.myccontadores.cobros.dto;


import cl.myccontadores.cobros.entity.Gasto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GastoDTO {

    private Long id;
    private ClienteDTO cliente;
    private FacturaDTO factura;
    private LocalDateTime fechaGasto;
    private Integer monto;
    private String descripcion;
    private ComprobanteDTO comprobante;


    public GastoDTO (Gasto model){
        this.id = model.getId();
        this.cliente = Optional.ofNullable(model.getCliente()).map(ClienteDTO::new).orElse(null);
        this.factura = Optional.ofNullable(model.getFactura()).map(FacturaDTO::new).orElse(null);
        this.fechaGasto = model.getFechaGasto();
        this.monto = model.getMonto();
        this.descripcion = model.getDescripcion();
        this.comprobante = Optional.ofNullable(model.getComprobante()).map(ComprobanteDTO::new).orElse(null);
    }

}
