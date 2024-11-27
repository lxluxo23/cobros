package cl.myccontadores.cobros.controller;

import cl.myccontadores.cobros.dto.ClienteDTO;
import cl.myccontadores.cobros.entity.Cliente;
import cl.myccontadores.cobros.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<ClienteDTO> obtenerTodosLosClientes() {
        return clienteService.obtenerTodosLosClientes().stream().map(ClienteDTO::new).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> obtenerClientePorId(@PathVariable("id") Long id) {
        Cliente cliente = clienteService.obtenerClientePorId(id);
        return ResponseEntity.ok(new ClienteDTO(cliente));
    }


    @PostMapping
    public ResponseEntity<ClienteDTO> crearCliente(@RequestBody ClienteDTO cliente) {
        Cliente nuevoCliente = clienteService.crearCliente(cliente);
        return ResponseEntity.ok(new ClienteDTO(nuevoCliente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id, @RequestBody Cliente clienteActualizado) {
        Cliente cliente = clienteService.actualizarCliente(id, clienteActualizado);
        return ResponseEntity.ok(cliente);
    } 

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable("id") Long id) {
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/morosos")
    public List<Cliente> obtenerClientesMorosos() {
        return clienteService.obtenerClientesMorosos();
    }
}