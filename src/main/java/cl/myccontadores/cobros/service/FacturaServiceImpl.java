package cl.myccontadores.cobros.service;

import cl.myccontadores.cobros.entity.Cliente;
import cl.myccontadores.cobros.entity.Factura;
import cl.myccontadores.cobros.entity.DetalleFactura;
import cl.myccontadores.cobros.entity.Gasto;
import cl.myccontadores.cobros.enums.EstadoFactura;
import cl.myccontadores.cobros.repository.FacturaRepository;
import cl.myccontadores.cobros.repository.GastoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import cl.myccontadores.cobros.exeptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacturaServiceImpl implements FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    GastoRepository gastoRepository;

    @Override
    public Factura crearFactura(Factura factura) {

        Cliente cliente = clienteService.obtenerClientePorId(factura.getCliente().getId());
        factura.setCliente(cliente);

        int montoDetalles = factura.getDetallesFactura().stream()
                .mapToInt(detalle -> detalle.getSubtotal() != null ? detalle.getSubtotal() : 0)
                .sum();
        List<Gasto> gastosPendientes = gastoRepository.findByClienteIdAndFacturaIsNull(cliente.getId());
        for (Gasto gasto : gastosPendientes) {
            gasto.setFactura(factura);
        }
        int montoGastos = gastosPendientes.stream()
                .mapToInt(gasto -> gasto.getMonto() != null ? gasto.getMonto() : 0)
                .sum();

        cliente.setSaldoPendiente(cliente.getSaldoPendiente() + montoGastos);
        clienteService.crearCliente(cliente);

        factura = facturaRepository.save(factura);
        gastoRepository.saveAll(gastosPendientes);

        cliente.setSaldoPendiente((int) (cliente.getSaldoPendiente() + factura.getMontoTotal()));

        clienteService.actualizarCliente(cliente.getId(), cliente);
        return factura;
    }

    @Override
    public Factura obtenerFacturaPorId(Long id) {
       Factura factura = facturaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Factura no encontrada con id: " + id));

        int montoDetalles = factura.getDetallesFactura().stream()
                .mapToInt(detalle -> detalle.getSubtotal() != null ? detalle.getSubtotal() : 0)
                .sum();

        int montoGastos = factura.getGastos().stream()
                .mapToInt(gasto -> gasto.getMonto() != null ? gasto.getMonto() : 0)
                .sum();

        factura.setMontoTotal((long) (montoDetalles + montoGastos));

       return factura;
    }

    @Override
    public List<Factura> obtenerFacturasPorClienteId(Long clienteId) {
        Cliente cliente = clienteService.obtenerClientePorId(clienteId);
        return facturaRepository.findByCliente(cliente);
    }

    @Override
    public Factura actualizarEstadoFactura(Long id, EstadoFactura estado) {
        Factura factura = obtenerFacturaPorId(id);
        factura.setEstado(estado);
        return facturaRepository.save(factura);
    }

    @Override
    public void eliminarFactura(Long id) {
        Factura factura = obtenerFacturaPorId(id);
        facturaRepository.delete(factura);
    }

}
