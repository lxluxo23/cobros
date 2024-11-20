package cl.myccontadores.cobros.entity;

import cl.myccontadores.cobros.dto.ItemDTO;
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
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;
    private String nombre;
    private String descripcion;
    @Column(name = "precio_unitario")
    private Integer precioUnitario;
    private String categoria;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<DetalleFactura> detallesFactura;

    @OneToOne
    @JoinColumn(name = "comprobante_id")
    @JsonManagedReference
    private Comprobante comprobante;

    public Item(Long id) {
        this.id = id;
    }
}
