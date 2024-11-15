package cl.myccontadores.cobros.dto;

import cl.myccontadores.cobros.entity.DetalleFactura;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetalleFacturaDTO implements Serializable {


    private Long id;
    private FacturaDTO factura;
    private ItemDTO item;
    private Integer cantidad;
    private Integer subtotal;

    public DetalleFacturaDTO(DetalleFactura model) {
        this.id = model.getId();
        this.factura = Optional.ofNullable(model.getFactura()).map(FacturaDTO::new).orElse(null);
        this.item = Optional.ofNullable(model.getItem()).map(ItemDTO::new).orElse(null);
        this.cantidad = model.getCantidad();
        this.subtotal = model.getSubtotal();
    }
}
