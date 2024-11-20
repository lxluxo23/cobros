package cl.myccontadores.cobros.service;

import cl.myccontadores.cobros.dto.PagoDTO;
import cl.myccontadores.cobros.entity.Cliente;
import cl.myccontadores.cobros.entity.Factura;
import cl.myccontadores.cobros.entity.Pago;
import cl.myccontadores.cobros.enums.EstadoFactura;
import cl.myccontadores.cobros.exeptions.ResourceNotFoundException;
import cl.myccontadores.cobros.repository.ClienteRepository;
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

    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public Pago registrarPago(PagoDTO pago) {
        Cliente cliente = clienteService.obtenerClientePorId(pago.getCliente().getId());

        Pago nuevoPago = new Pago(pago);
        nuevoPago.setCliente(cliente);

        if (nuevoPago.getFactura() != null) {
            Factura factura = facturaService.obtenerFacturaPorId(nuevoPago.getFactura().getId());
            nuevoPago.setFactura(factura);
        }

        cliente.setSaldoPendiente(cliente.getSaldoPendiente() - nuevoPago.getMonto());
        clienteService.actualizarCliente(cliente.getId(), cliente);


        if (nuevoPago.getFactura() != null) {
            Factura factura = nuevoPago.getFactura();
            if (cliente.getSaldoPendiente() <= 0) {
                factura.setEstado(EstadoFactura.PAGADA);
                facturaService.actualizarEstadoFactura(factura.getId(), EstadoFactura.PAGADA);
            }
        }

        return pagoRepository.save(nuevoPago);
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
