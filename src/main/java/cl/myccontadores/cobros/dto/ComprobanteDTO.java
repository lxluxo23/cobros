package cl.myccontadores.cobros.dto;

import cl.myccontadores.cobros.Message.ResponseFile;
import cl.myccontadores.cobros.entity.Comprobante;
import cl.myccontadores.cobros.enums.TipoComprobante;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComprobanteDTO implements Serializable {

    private Long id;
    private TipoComprobante tipo;
    private ResponseFile archivo;
    private LocalDateTime fecha;
    @JsonBackReference
    private ItemDTO item;


    public ComprobanteDTO(Comprobante model) {

        this.id = model.getId();
        this.tipo = model.getTipo();
        this.archivo = Optional.ofNullable(model.getFile()).map(file -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/archivos/")
                    .path(file.getId())
                    .toUriString();
            return new ResponseFile(file.getName(), fileDownloadUri, file.getType(), file.getData().length);
        }).orElse(null);
        this.fecha = model.getFecha();
        this.item = Optional.ofNullable(model.getItem()).map(item -> new ItemDTO(item.getId())).orElse(null);
    }

}
