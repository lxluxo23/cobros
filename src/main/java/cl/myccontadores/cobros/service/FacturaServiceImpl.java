package cl.myccontadores.cobros.service;

import cl.myccontadores.cobros.entity.Cliente;
import cl.myccontadores.cobros.entity.Factura;
import cl.myccontadores.cobros.entity.DetalleFactura;
import cl.myccontadores.cobros.enums.EstadoFactura;
import cl.myccontadores.cobros.repository.FacturaRepository;
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

    @Override
    public Factura crearFactura(Factura factura) {

        Cliente cliente = clienteService.obtenerClientePorId(factura.getCliente().getId());
        factura.setCliente(cliente);

        int montoTotal = factura.getDetallesFactura().stream()
                .mapToInt(DetalleFactura::getSubtotal)
                .sum();
        factura.setMontoTotal((long) montoTotal);


        cliente.setSaldoPendiente(cliente.getSaldoPendiente() + montoTotal);
        clienteService.crearCliente(cliente);

        return facturaRepository.save(factura);
    }

    @Override
    public Factura obtenerFacturaPorId(Long id) {
        return facturaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Factura no encontrada con id: " + id));
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
