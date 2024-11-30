package cl.myccontadores.cobros.service;

import cl.myccontadores.cobros.dto.DetallePagoDTO;
import cl.myccontadores.cobros.dto.request.CreateDetallePagoRequest;
import cl.myccontadores.cobros.entity.DetallePago;
import cl.myccontadores.cobros.entity.Item;
import cl.myccontadores.cobros.entity.Pago;
import cl.myccontadores.cobros.exeptions.ResourceNotFoundException;
import cl.myccontadores.cobros.repository.DetallePagoRepository;
import cl.myccontadores.cobros.repository.ItemRepository;
import cl.myccontadores.cobros.repository.PagoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetallePagoServiceImpl implements DetallePagoService {
    @Autowired
    DetallePagoRepository detallePagoRepository;
    @Autowired
    PagoRepository pagoRepository;
    @Autowired
    ItemRepository itemRepository;

    @Override
    @Transactional
    public DetallePagoDTO agregarDetallePago(CreateDetallePagoRequest request) {
        Pago pago = pagoRepository.findById(request.getPagoId())
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado"));

        Item item = itemRepository.findById(request.getItemId())
                .orElseThrow(() -> new ResourceNotFoundException("Item no encontrado"));

        DetallePago detalle = DetallePago.builder()
                .pago(pago)
                .item(item)
                .cantidad(request.getCantidad())
                .montoUnitario(item.getPrecioUnitario())
                .montoTotal(item.getPrecioUnitario() * request.getCantidad())
                .descripcion(request.getDescripcion())
                .build();
        return new DetallePagoDTO(detallePagoRepository.save(detalle));
    }

    @Override
    public List<DetallePagoDTO> findByPagoId(Long pagoId) {
        return detallePagoRepository.findByPagoId(pagoId).stream()
                .map(DetallePagoDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public DetallePagoDTO findById(Long id) {
        return detallePagoRepository.findById(id)
                .map(DetallePagoDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException("Detalle de pago no encontrado"));
    }

    @Override
    @Transactional
    public void deleteDetallePago(Long id) {
        if (!detallePagoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Detalle de pago no encontrado");
        }
        detallePagoRepository.deleteById(id);
    }
}
