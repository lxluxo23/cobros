package cl.myccontadores.cobros.utils;

import cl.myccontadores.cobros.entity.*;
import cl.myccontadores.cobros.enums.EstadoFactura;
import cl.myccontadores.cobros.enums.TipoComprobante;
import cl.myccontadores.cobros.repository.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@Log4j2
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
    private ComprobanteRepository comprobanteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;
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

        clienteRepository.save(cliente1);

        // Crear métodos de pago
        MetodoPago efectivo = MetodoPago.builder().nombre("Efectivo").build();
        MetodoPago transferencia = MetodoPago.builder().nombre("Transferencia").build();
        metodoPagoRepository.saveAll(Arrays.asList(efectivo, transferencia));

        // Crear ítems
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

        // Crear factura para cliente1
        Factura factura1 = Factura.builder()
                .cliente(cliente1)
                .fechaEmision(LocalDate.now().atStartOfDay())
                .mesCorrespondiente((byte) 1)
                .montoTotal(0L)
                .estado(EstadoFactura.PENDIENTE)
                .build();

        facturaRepository.save(factura1);

        // Crear detalles de factura
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

        // Calcular monto total de la factura
        int montoTotalFactura = detalle1.getSubtotal() + detalle2.getSubtotal();
        factura1.setMontoTotal((long) montoTotalFactura);
        facturaRepository.save(factura1);

        // Actualizar saldo pendiente del cliente
        cliente1.setSaldoPendiente(cliente1.getSaldoPendiente() + montoTotalFactura);
        clienteRepository.save(cliente1);

        // Crear comprobante de pago
        Comprobante comprobantePago = Comprobante.builder()
                .tipo(TipoComprobante.PAGO)
                .fecha(LocalDate.now().atStartOfDay())
                .file(null) // Suponiendo que el archivo se cargará después
                .build();

        comprobanteRepository.save(comprobantePago);

        // Registrar un pago parcial
        Pago pago1 = Pago.builder()
                .cliente(cliente1)
                .factura(factura1)
                .fechaPago(LocalDate.now().atStartOfDay())
                .monto(5000) // Pago parcial
                .metodoPago(efectivo)
                .comprobante(comprobantePago)
                .build();

        pagoRepository.save(pago1);

        // Actualizar saldo pendiente del cliente después del pago
        cliente1.setSaldoPendiente(cliente1.getSaldoPendiente() - pago1.getMonto());
        clienteRepository.save(cliente1);

        // Actualizar estado de la factura si está pagada
        if (cliente1.getSaldoPendiente() <= 0) {
            factura1.setEstado(EstadoFactura.PAGADA);
            facturaRepository.save(factura1);
        }

        // Crear comprobante de gasto
        Comprobante comprobanteGasto = Comprobante.builder()
                .tipo(TipoComprobante.GASTO)
                .fecha(LocalDate.now().atStartOfDay())
                .file(null) // Suponiendo que el archivo se cargará después
                .build();

        comprobanteRepository.save(comprobanteGasto);

        // Registrar un gasto para cliente1


        // Actualizar saldo pendiente del cliente con el gasto
        cliente1.setSaldoPendiente(cliente1.getSaldoPendiente());
        clienteRepository.save(cliente1);

        // Crear usuarios
        Usuario u1 = Usuario.builder()
                .nombreUsuario("admin")
                .nombre("Admin")
                .apellido("Admin")
                .email("admin@example.com")
                .password(passwordEncoder.encode("caca1234"))
                .build();

        Usuario u2 = Usuario.builder()
                .nombreUsuario("andrea")
                .nombre("Andrea")
                .apellido("Cespedes")
                .email("a.cespedes@myccontadores.cl")
                .password(passwordEncoder.encode("7689myc"))
                .build();

        usuarioRepository.saveAll(Arrays.asList(u1, u2));

        log.info("Datos de inicialización cargados exitosamente.");

    }
}
