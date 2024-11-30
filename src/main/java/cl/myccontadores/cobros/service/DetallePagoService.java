package cl.myccontadores.cobros.service;

import cl.myccontadores.cobros.dto.DetallePagoDTO;
import cl.myccontadores.cobros.dto.request.CreateDetallePagoRequest;

import java.util.List;

public interface DetallePagoService {
    DetallePagoDTO agregarDetallePago(CreateDetallePagoRequest request);
    List<DetallePagoDTO> findByPagoId(Long pagoId);
    DetallePagoDTO findById(Long id);
    void deleteDetallePago(Long id);
}
