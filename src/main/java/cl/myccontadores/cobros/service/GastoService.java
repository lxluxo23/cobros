package cl.myccontadores.cobros.service;

import cl.myccontadores.cobros.entity.Gasto;

import java.util.List;

public interface GastoService {
    Gasto registrarGasto(Gasto gasto,Long clienteId,Long comprobanteId);
    Gasto obtenerGastoPorId(Long id);
    List<Gasto> obtenerGastosPorClienteId(Long clienteId);
    void eliminarGasto(Long id);
}
