package cl.myccontadores.cobros.dto;

import cl.myccontadores.cobros.entity.MetodoPago;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MetodoPagoDTO implements Serializable {

    private Long id;
    private String nombre;

    public MetodoPagoDTO (MetodoPago model){
        this.id = model.getId();
        this.nombre = model.getNombre();
    }
}
