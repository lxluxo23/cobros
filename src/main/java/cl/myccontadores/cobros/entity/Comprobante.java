package cl.myccontadores.cobros.entity;

import cl.myccontadores.cobros.dto.ComprobanteDTO;
import cl.myccontadores.cobros.enums.TipoComprobante;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "comprobantes")
public class Comprobante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comprobante_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoComprobante tipo;

    @OneToOne
    @JoinColumn(name = "file_id")
    private FileDB file;

    private LocalDateTime fecha;

    @OneToOne(mappedBy = "comprobante")
    @JsonBackReference
    private Item item;

    public Comprobante(Long id) {
        this.id = id;
    }

    public Comprobante(ComprobanteDTO dto) {
        this.id = dto.getId();
        this.tipo = dto.getTipo();
        this.fecha = dto.getFecha();
        this.item = Optional.ofNullable(dto.getItem()).map(item->new Item(item.getId())).orElse(null);
    }
}
