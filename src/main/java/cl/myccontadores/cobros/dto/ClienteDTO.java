package cl.myccontadores.cobros.dto;

import cl.myccontadores.cobros.entity.Cliente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteDTO implements Serializable {

    private Long id;
    private String rut;
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;
    private Integer saldoPendiente ;

    public ClienteDTO (Cliente model){
        this.id = model.getId();
        this.rut = model.getRut();
        this.nombre = model.getNombre();
        this.direccion = model.getDireccion();
        this.telefono = model.getTelefono();
        this.email = model.getEmail();
        this.saldoPendiente = model.getSaldoPendiente();
    }
}
