package cl.myccontadores.cobros.service;

import cl.myccontadores.cobros.dto.ComprobanteDTO;
import cl.myccontadores.cobros.entity.Comprobante;

public interface ComprobanteService {
    Comprobante crearComprobante(Comprobante comprobante);
    ComprobanteDTO obtenerComprobantePorId(Long id);
    void borrarComprobante(Long id);
}
