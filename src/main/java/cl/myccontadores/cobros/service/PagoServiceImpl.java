package cl.myccontadores.cobros.service;

import cl.myccontadores.cobros.entity.Cliente;
import cl.myccontadores.cobros.entity.Factura;
import cl.myccontadores.cobros.entity.Pago;
import cl.myccontadores.cobros.enums.EstadoFactura;
import cl.myccontadores.cobros.exeptions.ResourceNotFoundException;
import cl.myccontadores.cobros.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagoServiceImpl implements PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private FacturaService facturaService;

    @Override
    public Pago registrarPago(Pago pago) {

        Cliente cliente = clienteService.obtenerClientePorId(pago.getCliente().getId());
        pago.setCliente(cliente);


        if (pago.getFactura() != null) {
            Factura factura = facturaService.obtenerFacturaPorId(pago.getFactura().getId());
            pago.setFactura(factura);
        }

        cliente.setSaldoPendiente(cliente.getSaldoPendiente() - pago.getMonto());
        clienteService.crearCliente(cliente);

        if (pago.getFactura() != null) {
            Factura factura = pago.getFactura();
            if (cliente.getSaldoPendiente() <= 0) {
                factura.setEstado(EstadoFactura.PAGADA);
                facturaService.actualizarEstadoFactura(factura.getId(), EstadoFactura.PAGADA);
            }
        }

        return pagoRepository.save(pago);
    }

    @Override
    public Pago obtenerPagoPorId(Long id) {
        return pagoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado con id: " + id));
    }

    @Override
    public List<Pago> obtenerPagosPorClienteId(Long clienteId) {
        Cliente cliente = clienteService.obtenerClientePorId(clienteId);
        return pagoRepository.findByCliente(cliente);
    }

    @Override
    public void eliminarPago(Long id) {
        Pago pago = obtenerPagoPorId(id);
        pagoRepository.delete(pago);
    }
}
