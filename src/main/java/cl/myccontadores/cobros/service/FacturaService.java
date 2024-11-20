package cl.myccontadores.cobros.service;

import cl.myccontadores.cobros.dto.DetalleFacturaDTO;
import cl.myccontadores.cobros.dto.FacturaDTO;
import cl.myccontadores.cobros.entity.Factura;
import cl.myccontadores.cobros.enums.EstadoFactura;

import java.util.List;

public interface FacturaService {
    FacturaDTO crearFactura(FacturaDTO facturaDTO);
    Factura agregarDetalleFactura(DetalleFacturaDTO detalleDTO);
    Factura obtenerFacturaPorId(Long id);
    List<Factura> obtenerFacturasPorClienteId(Long clienteId);
    Factura actualizarEstadoFactura(Long id, EstadoFactura estado);
    void eliminarFactura(Long id);
}

