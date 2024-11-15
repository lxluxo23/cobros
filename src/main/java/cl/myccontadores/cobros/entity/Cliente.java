package cl.myccontadores.cobros.entity;

import cl.myccontadores.cobros.dto.ClienteDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cliente_id")
    private Long id;
    private String rut;
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;
    private Integer saldoPendiente;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Factura> facturas;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Pago> pagos;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Gasto> gastos;


    public Cliente(ClienteDTO dto) {
        this.id = dto.getId();
        this.rut = dto.getRut();
        this.nombre = dto.getNombre();
        this.direccion = dto.getDireccion();
        this.telefono = dto.getTelefono();
        this.email = dto.getEmail();
        this.saldoPendiente = dto.getSaldoPendiente();
    }

}
