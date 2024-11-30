package cl.myccontadores.cobros.service;

import cl.myccontadores.cobros.dto.PagoDTO;
import cl.myccontadores.cobros.dto.request.CrearPagoRequestDTO;
import org.apache.coyote.BadRequestException;

import java.io.IOException;
import java.util.List;

public interface PagoService {
    PagoDTO registrarPago(CrearPagoRequestDTO request) throws BadRequestException;
    PagoDTO findById(Long id);
    List<PagoDTO> findAll();
    void deletePago(Long id);
}
