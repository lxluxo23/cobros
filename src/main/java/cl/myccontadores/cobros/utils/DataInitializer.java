package cl.myccontadores.cobros.utils;

import cl.myccontadores.cobros.entity.*;
import cl.myccontadores.cobros.enums.EstadoFactura;
import cl.myccontadores.cobros.enums.TipoComprobante;
import cl.myccontadores.cobros.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private DetalleFacturaRepository detalleFacturaRepository;

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    @Autowired
    private GastoRepository gastoRepository;

    @Autowired
    private ComprobanteRepository comprobanteRepository;
    @Override
    public void run(String... args) throws Exception {
        Cliente cliente1 = Cliente.builder()
                .nombre("Cliente A")
                .rut("19474665-2")
                .direccion("Dirección A")
                .telefono("123456789")
                .email("clienteA@example.com")
                .saldoPendiente(0)
                .build();

        Cliente cliente2 = Cliente.builder()
                .nombre("Cliente B")
                .rut("11111111-1")
                .direccion("Dirección B")
                .telefono("987654321")
                .email("clienteB@example.com")
                .saldoPendiente(0)
                .build();

        clienteRepository.saveAll(Arrays.asList(cliente1, cliente2));


        MetodoPago efectivo = MetodoPago.builder().nombre("Efectivo").build();
        MetodoPago transferencia = MetodoPago.builder().nombre("Transferencia").build();
        metodoPagoRepository.saveAll(Arrays.asList(efectivo, transferencia));

        Item iva = Item.builder()
                .nombre("IVA")
                .descripcion("Impuesto al Valor Agregado")
                .precioUnitario(1900)
                .categoria("Impuesto")
                .build();

        Item honorarios = Item.builder()
                .nombre("Honorarios")
                .descripcion("Pago por servicios profesionales")
                .precioUnitario(5000)
                .categoria("Servicio")
                .build();

        itemRepository.saveAll(Arrays.asList(iva, honorarios));

        Factura factura1 = Factura.builder()
                .cliente(cliente1)
                .fechaEmision(LocalDate.now().atStartOfDay())
                .mesCorrespondiente((byte) 1)
                .montoTotal(0L)
                .estado(EstadoFactura.PENDIENTE)
                .build();

        facturaRepository.save(factura1);

        DetalleFactura detalle1 = DetalleFactura.builder()
                .factura(factura1)
                .item(iva)
                .cantidad(3)
                .subtotal(iva.getPrecioUnitario() * 3)
                .build();

        DetalleFactura detalle2 = DetalleFactura.builder()
                .factura(factura1)
                .item(honorarios)
                .cantidad(2)
                .subtotal(honorarios.getPrecioUnitario() * 2)
                .build();

        detalleFacturaRepository.saveAll(Arrays.asList(detalle1, detalle2));

        int montoTotalFactura = detalle1.getSubtotal() + detalle2.getSubtotal();
        factura1.setMontoTotal((long) montoTotalFactura);
        facturaRepository.save(factura1);

        cliente1.setSaldoPendiente(cliente1.getSaldoPendiente() + montoTotalFactura);
        clienteRepository.save(cliente1);

        Comprobante comprobantePago = Comprobante.builder()
                .tipo(TipoComprobante.PAGO)
                .fecha(LocalDate.now().atStartOfDay())
                .build();

        comprobanteRepository.save(comprobantePago);

        Pago pago1 = Pago.builder()
                .cliente(cliente1)
                .factura(factura1)
                .fechaPago(LocalDate.now().atStartOfDay())
                .monto(5000) // Pago parcial
                .metodoPago(efectivo)
                .comprobante(comprobantePago)
                .build();

        pagoRepository.save(pago1);


        cliente1.setSaldoPendiente(cliente1.getSaldoPendiente() - pago1.getMonto());
        clienteRepository.save(cliente1);

        if (cliente1.getSaldoPendiente() <= 0) {
            factura1.setEstado(EstadoFactura.PAGADA);
            facturaRepository.save(factura1);
        }

        Comprobante comprobanteGasto = Comprobante.builder()
                .tipo(TipoComprobante.GASTO)
                .fecha(LocalDateTime.now())
                .build();

        comprobanteRepository.save(comprobanteGasto);


        Gasto gasto1 = Gasto.builder()
                .cliente(cliente2)
                .fechaGasto(LocalDateTime.now())
                .monto(3200)
                .descripcion("Pago de impuestos")
                .comprobante(comprobanteGasto)
                .build();

        gastoRepository.save(gasto1);

        cliente2.setSaldoPendiente(cliente2.getSaldoPendiente() + gasto1.getMonto());
        clienteRepository.save(cliente2);
        System.out.println("Datos de inicialización cargados exitosamente.");

    }
}
