package cl.myccontadores.cobros.entity;

import cl.myccontadores.cobros.enums.TipoComprobante;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

}
