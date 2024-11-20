package cl.myccontadores.cobros.service;

import cl.myccontadores.cobros.dto.PagoDTO;
import cl.myccontadores.cobros.entity.Pago;

import java.util.List;

public interface PagoService {
    Pago registrarPago(PagoDTO pago);
    Pago obtenerPagoPorId(Long id);
    List<Pago> obtenerPagosPorClienteId(Long clienteId);
    void eliminarPago(Long id);
}
