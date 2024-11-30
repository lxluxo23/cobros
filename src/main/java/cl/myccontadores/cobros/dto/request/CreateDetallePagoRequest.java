package cl.myccontadores.cobros.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDetallePagoRequest {
    private Long pagoId;
    private Long itemId;
    private Integer cantidad;
    private String descripcion;
}
