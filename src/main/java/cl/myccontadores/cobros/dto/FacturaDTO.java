package cl.myccontadores.cobros.dto;

import cl.myccontadores.cobros.entity.Cliente;
import cl.myccontadores.cobros.entity.Factura;
import cl.myccontadores.cobros.enums.EstadoFactura;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FacturaDTO implements Serializable {

    private Long id;
    private ClienteDTO cliente;
    private LocalDateTime fechaEmision;
    private Byte mesCorrespondiente;
    private Integer anio;
    private Long montoTotal;
    @Enumerated(EnumType.STRING)
    private EstadoFactura estado;
    @JsonManagedReference
    private List<DetalleFacturaDTO> detallesFactura;
    @JsonManagedReference
    private List<PagoDTO> pagos;

    public FacturaDTO(Long id) {
        this.id = id;
    }

    public FacturaDTO(Factura model) {
        this.id = model.getId();
        this.cliente = Optional.ofNullable(model.getCliente()).map(ClienteDTO::new).orElse(null);
        this.fechaEmision = model.getFechaEmision();
        this.mesCorrespondiente = model.getMesCorrespondiente();
        this.anio = model.getAnio();
        this.montoTotal = model.getMontoTotal();
        this.estado = model.getEstado();
        this.detallesFactura = Optional.ofNullable(model.getDetallesFactura()).map(detallesFactura -> detallesFactura.stream().map(DetalleFacturaDTO::new).toList()).orElse(null);
        this.pagos = Optional.ofNullable(model.getPagos()).map(pagos -> pagos.stream().map(PagoDTO::new).toList()).orElse(null);
    }
}
