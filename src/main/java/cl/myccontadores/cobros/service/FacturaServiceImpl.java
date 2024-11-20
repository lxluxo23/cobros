package cl.myccontadores.cobros.service;

import cl.myccontadores.cobros.dto.DetalleFacturaDTO;
import cl.myccontadores.cobros.dto.FacturaDTO;
import cl.myccontadores.cobros.entity.Cliente;
import cl.myccontadores.cobros.entity.DetalleFactura;
import cl.myccontadores.cobros.entity.Factura;
import cl.myccontadores.cobros.entity.Item;
import cl.myccontadores.cobros.enums.EstadoFactura;
import cl.myccontadores.cobros.repository.ClienteRepository;
import cl.myccontadores.cobros.repository.DetalleFacturaRepository;
import cl.myccontadores.cobros.repository.FacturaRepository;
import cl.myccontadores.cobros.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import cl.myccontadores.cobros.exeptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.Optional;

@Service
public class FacturaServiceImpl implements FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private DetalleFacturaRepository detalleFacturaRepository;

    @Autowired
    private ClienteRepository clienteRepository;


    public FacturaDTO crearFactura(FacturaDTO facturaDTO) {
        Cliente cliente = clienteRepository.findById(facturaDTO.getCliente().getId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + facturaDTO.getCliente().getId()));

        LocalDateTime fechaEmision = Optional.ofNullable(facturaDTO.getFechaEmision())
                .orElse(LocalDateTime.now());

        byte mesCorrespondiente = Optional.ofNullable(facturaDTO.getMesCorrespondiente())
                .orElse((byte) fechaEmision.getMonthValue());

        Year anioActual = Year.of(fechaEmision.getYear());


        Factura factura = Factura.builder()
                .cliente(cliente)
                .fechaEmision(fechaEmision)
                .mesCorrespondiente(mesCorrespondiente)
                .anio(anioActual.getValue())
                .estado(EstadoFactura.PENDIENTE)
                .montoTotal(0L)
                .build();

        factura = facturaRepository.save(factura);

        return new FacturaDTO(factura);
    }

    @Override
    public Factura agregarDetalleFactura(DetalleFacturaDTO detalleDTO) {
        Factura factura = facturaRepository.findById(detalleDTO.getFactura().getId())
                .orElseThrow(() -> new RuntimeException("Factura no encontrada con id: " + detalleDTO.getFactura().getId()));
        Item item = itemRepository.findById(detalleDTO.getItem().getId())
                .orElseThrow(() -> new RuntimeException("Ítem no encontrado con id: " + detalleDTO.getItem().getId()));

        DetalleFactura nuevoDetalle = DetalleFactura.builder()
                .factura(factura)
                .item(item)
                .cantidad(detalleDTO.getCantidad())
                .subtotal(item.getPrecioUnitario() * detalleDTO.getCantidad())
                .build();
        detalleFacturaRepository.save(nuevoDetalle);

        int nuevoMontoTotal = factura.getDetallesFactura().stream()
                .mapToInt(DetalleFactura::getSubtotal)
                .sum();
        factura.setMontoTotal((long) nuevoMontoTotal);
        facturaRepository.save(factura);

        return factura;
    }

    @Override
    public Factura obtenerFacturaPorId(Long id) {
        Factura factura = facturaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Factura no encontrada con id: " + id));

        int montoDetalles = factura.getDetallesFactura().stream()
                .mapToInt(detalle -> detalle.getSubtotal() != null ? detalle.getSubtotal() : 0)
                .sum();

        factura.setMontoTotal((long) (montoDetalles));

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
