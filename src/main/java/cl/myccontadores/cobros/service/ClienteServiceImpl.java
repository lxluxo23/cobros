package cl.myccontadores.cobros.service;

import cl.myccontadores.cobros.dto.ClienteDTO;
import cl.myccontadores.cobros.entity.Cliente;
import cl.myccontadores.cobros.entity.Factura;
import cl.myccontadores.cobros.enums.EstadoFactura;
import cl.myccontadores.cobros.repository.ClienteRepository;
import cl.myccontadores.cobros.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import cl.myccontadores.cobros.exeptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private FacturaRepository facturaRepository;


    @Override
    public Cliente crearCliente(ClienteDTO cliente) {
        Cliente nuevoCliente = new Cliente(cliente);
        Cliente clienteGuardado = clienteRepository.save(nuevoCliente);

        Factura factura = Factura.builder()
                .cliente(clienteGuardado)
                .fechaEmision(LocalDateTime.now())
                .anio(LocalDate.now().getYear())
                .mesCorrespondiente((byte) LocalDate.now().getMonthValue())
                .montoTotal(0L)
                .estado(EstadoFactura.PAGADA)
                .build();

        facturaRepository.save(factura);

        return clienteGuardado;
    }

    @Override
    public Cliente obtenerClientePorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));
    }

    @Override
    public List<Cliente> obtenerTodosLosClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente actualizarCliente(Long id, Cliente clienteActualizado) {
        Cliente clienteExistente = obtenerClientePorId(id);
        clienteExistente.setNombre(clienteActualizado.getNombre());
        clienteExistente.setDireccion(clienteActualizado.getDireccion());
        clienteExistente.setTelefono(clienteActualizado.getTelefono());
        clienteExistente.setEmail(clienteActualizado.getEmail());
        return clienteRepository.save(clienteExistente);
    }

    @Override
    public void eliminarCliente(Long id) {
        Cliente cliente = obtenerClientePorId(id);
        clienteRepository.delete(cliente);
    }

    @Override
    public List<Cliente> obtenerClientesMorosos() {
        return clienteRepository.findBySaldoPendienteGreaterThan(0);
    }
}
