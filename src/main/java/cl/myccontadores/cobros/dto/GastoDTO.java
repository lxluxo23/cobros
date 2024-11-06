package cl.myccontadores.cobros.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GastoDTO {

    private Long clienteId;
    private Integer monto;
    private String descripcion;
    private LocalDate fecha;
    private Long comprobanteId;

}
