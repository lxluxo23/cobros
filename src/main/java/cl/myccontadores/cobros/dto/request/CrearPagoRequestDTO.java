package cl.myccontadores.cobros.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrearPagoRequestDTO {
    private Long clienteId;
    private Long facturaId;
    private Integer monto;
    private Long metodoPagoId;
    private ComprobanteRequest comprobante;

    public CrearPagoRequestDTO(Long clienteId) {
        this.clienteId = clienteId;
    }
}
