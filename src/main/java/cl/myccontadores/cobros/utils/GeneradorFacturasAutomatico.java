package cl.myccontadores.cobros.utils;

import cl.myccontadores.cobros.entity.Cliente;
import cl.myccontadores.cobros.entity.Factura;
import cl.myccontadores.cobros.enums.EstadoFactura;
import cl.myccontadores.cobros.repository.ClienteRepository;
import cl.myccontadores.cobros.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;

@EnableScheduling
public class GeneradorFacturasAutomatico {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    @Scheduled(cron = "0 0 0 1 * ?")
    public void generarFacturasMensuales() {
        List<Cliente> clientes = clienteRepository.findAll();

        LocalDate fechaActual = LocalDate.now();
        Byte mesActual = (byte) fechaActual.getMonthValue();
        Year anioActual = Year.of(fechaActual.getYear());

        for (Cliente cliente : clientes) {
            boolean existeFactura = facturaRepository.existsByClienteIdAndMesCorrespondienteAndAnio(
                    cliente.getId(), mesActual, anioActual.getValue());

            if (!existeFactura) {
                Factura factura = new Factura();
                factura.setCliente(cliente);
                factura.setFechaEmision(LocalDateTime.now());
                factura.setMesCorrespondiente(mesActual);
                factura.setAnio(anioActual.getValue());
                factura.setEstado(EstadoFactura.PENDIENTE);
                facturaRepository.save(factura);
            }
        }
    }
}
