package cl.myccontadores.cobros.entity;

import cl.myccontadores.cobros.dto.FacturaDTO;
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
import java.util.Optional;

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
    @JsonBackReference("cliente-factura")
    private Cliente cliente;

    private LocalDateTime fechaEmision;
    private Byte mesCorrespondiente;
    private Integer anio;
    private Long montoTotal;

    @Enumerated(EnumType.STRING)
    private EstadoFactura estado;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<DetalleFactura> detallesFactura;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Pago> pagos;

    public Factura(Long id) {
        this.id = id;
    }

    public Factura(FacturaDTO dto) {
        this.id = dto.getId();
        this.cliente = Optional.ofNullable(dto.getCliente()).map(cliente->new Cliente(cliente.getId())).orElse(null);
        this.fechaEmision = dto.getFechaEmision();
        this.mesCorrespondiente = dto.getMesCorrespondiente();
        this.anio = dto.getAnio();
        this.montoTotal = dto.getMontoTotal();
        this.estado = dto.getEstado();
    }


}
