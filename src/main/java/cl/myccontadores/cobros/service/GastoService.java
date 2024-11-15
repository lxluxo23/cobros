package cl.myccontadores.cobros.service;

import cl.myccontadores.cobros.dto.GastoDTO;
import cl.myccontadores.cobros.entity.Gasto;

import java.util.List;

public interface GastoService {
    GastoDTO registrarGasto(Gasto gasto, Long clienteId, Long comprobanteId,Long facturaId);
    Gasto obtenerGastoPorId(Long id);
    List<Gasto> obtenerGastosPorClienteId(Long clienteId);
    void eliminarGasto(Long id);
}
