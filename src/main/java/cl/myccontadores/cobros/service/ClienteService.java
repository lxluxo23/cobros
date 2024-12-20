package cl.myccontadores.cobros.service;

import cl.myccontadores.cobros.dto.ClienteDTO;
import cl.myccontadores.cobros.entity.Cliente;

import java.util.List;

public interface ClienteService {
    Cliente crearCliente(ClienteDTO cliente);
    Cliente obtenerClientePorId(Long id);
    List<Cliente> obtenerTodosLosClientes();
    Cliente actualizarCliente(Long id, Cliente clienteActualizado);
    void eliminarCliente(Long id);
    List<Cliente> obtenerClientesMorosos();
}
