package cl.myccontadores.cobros.service;

import cl.myccontadores.cobros.Message.ResponseFile;
import cl.myccontadores.cobros.dto.ComprobanteDTO;
import cl.myccontadores.cobros.dto.PagoDTO;
import cl.myccontadores.cobros.dto.request.CrearPagoRequestDTO;
import cl.myccontadores.cobros.entity.*;
import cl.myccontadores.cobros.enums.EstadoFactura;
import cl.myccontadores.cobros.exeptions.BadRequestException;
import cl.myccontadores.cobros.exeptions.ResourceNotFoundException;
import cl.myccontadores.cobros.repository.ClienteRepository;
import cl.myccontadores.cobros.repository.FacturaRepository;
import cl.myccontadores.cobros.repository.MetodoPagoRepository;
import cl.myccontadores.cobros.repository.PagoRepository;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private MetodoPagoRepository metodoPagoRepository;



    @Override
    public PagoDTO registrarPago(CrearPagoRequestDTO request) throws BadRequestException {
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
        Factura factura = facturaRepository.findById(request.getFacturaId())
                .orElseThrow(() -> new ResourceNotFoundException("Factura no encontrada"));

        MetodoPago metodoPago = metodoPagoRepository.findById(request.getMetodoPagoId())
                .orElseThrow(() -> new ResourceNotFoundException("Método de pago no encontrado"));

        validatePayment(factura, request.getMonto());

        Pago pago = Pago.builder()
                .cliente(cliente)
                .factura(factura)
                .monto(request.getMonto())
                .metodoPago(metodoPago)
                .fechaPago(LocalDateTime.now())
                .build();
        actualizarEstadoFactura(factura, pago);
        return new PagoDTO(pagoRepository.save(pago));

    }



    @Override
    public PagoDTO findById(Long id) {
        return pagoRepository.findById(id)
                .map(PagoDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado"));
    }

    @Override
    public List<PagoDTO> findAll() {
        return pagoRepository.findAll().stream()
                .map(PagoDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deletePago(Long id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado"));
        Factura factura = pago.getFactura();
        if (factura.getEstado() == EstadoFactura.PAGADA) {
            factura.setEstado(EstadoFactura.PENDIENTE);
            facturaRepository.save(factura);
        }
        pagoRepository.deleteById(id);

    }

    private void validatePayment(Factura factura, Integer monto) throws BadRequestException {
        if (factura.isPagada()) {
            throw new BadRequestException("La factura ya está pagada");
        }
        if (monto > factura.getSaldoPendiente()) {
            throw new BadRequestException(
                    String.format("El monto del pago (%d) excede el saldo pendiente (%d)",
                            monto, factura.getSaldoPendiente())
            );
        }
    }
    private void actualizarEstadoFactura(Factura factura, Pago nuevoPago) {
        int nuevoSaldo = factura.getSaldoPendiente() - nuevoPago.getMonto();
        if (nuevoSaldo <= 0) {
            factura.setEstado(EstadoFactura.PAGADA);
        } else if (nuevoSaldo < factura.getMontoTotal()) {
            factura.setEstado(EstadoFactura.PENDIENTE);
        }
        facturaRepository.save(factura);
    }
}
