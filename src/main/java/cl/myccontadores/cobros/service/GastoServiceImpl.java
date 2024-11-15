package cl.myccontadores.cobros.service;

import cl.myccontadores.cobros.entity.Cliente;
import cl.myccontadores.cobros.entity.Comprobante;
import cl.myccontadores.cobros.entity.Gasto;
import cl.myccontadores.cobros.repository.ComprobanteRepository;
import cl.myccontadores.cobros.repository.GastoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import cl.myccontadores.cobros.exeptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GastoServiceImpl implements GastoService {

    @Autowired
    private GastoRepository gastoRepository;

    @Autowired
    private ComprobanteRepository comprobanteRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ComprobanteService comprobanteService;

    @Override
    public Gasto registrarGasto(Gasto gasto, Long clienteId, Long comprobanteId) {


        Cliente cliente = clienteService.obtenerClientePorId(clienteId);
        gasto.setCliente(cliente);
        if (comprobanteId != null) {
            Comprobante comprobante = comprobanteRepository.findById(comprobanteId).orElseThrow(RuntimeException::new);
            gasto.setComprobante(comprobante);
        }

        cliente.setSaldoPendiente(cliente.getSaldoPendiente() + gasto.getMonto());
        clienteService.crearCliente(cliente);

        return gastoRepository.save(gasto);
    }

    @Override
    public Gasto obtenerGastoPorId(Long id) {
        return gastoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gasto no encontrado con id: " + id));
    }

    @Override
    public List<Gasto> obtenerGastosPorClienteId(Long clienteId) {
        Cliente cliente = clienteService.obtenerClientePorId(clienteId);
        return gastoRepository.findByCliente(cliente);
    }

    @Override
    public void eliminarGasto(Long id) {
        Gasto gasto = obtenerGastoPorId(id);
        gastoRepository.delete(gasto);
    }
}
