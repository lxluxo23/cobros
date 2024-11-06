package cl.myccontadores.cobros.entity;

import cl.myccontadores.cobros.enums.EstadoFactura;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "facturas")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "factura_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id",nullable = false)
    private Cliente cliente;
    private LocalDateTime fechaEmision;
    private Byte mesCorrespondiente;
    private Long montoTotal;
    @Enumerated(EnumType.STRING)
    private EstadoFactura estado;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<DetalleFactura> detallesFactura;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Pago> pagos;
}
