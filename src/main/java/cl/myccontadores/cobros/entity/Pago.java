package cl.myccontadores.cobros.entity;

import cl.myccontadores.cobros.dto.PagoDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "pagos")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pago_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id",nullable = false)
    @JsonBackReference
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "factura_id")
    @JsonBackReference
    private Factura factura;


    private LocalDateTime fechaPago;
    private Integer monto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "metodo_pago_id", nullable = false)
    private MetodoPago metodoPago;


    @OneToMany(mappedBy = "pago", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<DetallePago> detallesPago;

    public Pago(Long id) {
        this.id = id;
    }
    public Pago(PagoDTO dto) {
        this.id = dto.getId();
        this.cliente = Optional.ofNullable(dto.getCliente()).map(cliente->new Cliente(cliente.getId())).orElse(null);
        this.factura = Optional.ofNullable(dto.getFactura()).map(factura->new Factura(factura.getId())).orElse(null);
        this.fechaPago = dto.getFechaPago();
        this.monto = dto.getMonto();
        this.metodoPago = Optional.ofNullable(dto.getMetodoPago()).map(metodoPago->new MetodoPago(metodoPago.getId())).orElse(null);
    }
}
