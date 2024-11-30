package cl.myccontadores.cobros.dto;

import cl.myccontadores.cobros.entity.DetallePago;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetallePagoDTO {
    private Long id;
    private ItemDTO item;
    private Integer cantidad;
    private Integer montoUnitario;
    private Integer montoTotal;
    private String descripcion;

    public DetallePagoDTO(DetallePago detalle) {
        this.id = detalle.getId();
        this.item = Optional.ofNullable(detalle.getItem()).map(ItemDTO::new).orElse(null);
        this.cantidad = detalle.getCantidad();
        this.montoUnitario = detalle.getMontoUnitario();
        this.montoTotal = detalle.getMontoTotal();
        this.descripcion = detalle.getDescripcion();
    }
}
