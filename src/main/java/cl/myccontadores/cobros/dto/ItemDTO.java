package cl.myccontadores.cobros.dto;

import cl.myccontadores.cobros.entity.Item;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDTO implements Serializable {

    private Long id;
    private String nombre;
    private String descripcion;
    private Integer precioUnitario;
    private String categoria;

    public ItemDTO(Item model) {
        this.id = model.getId();
        this.nombre = model.getNombre();
        this.descripcion = model.getDescripcion();
        this.precioUnitario = model.getPrecioUnitario();
        this.categoria = model.getCategoria();
    }


}
