package cl.myccontadores.cobros.service;

import cl.myccontadores.cobros.dto.ComprobanteDTO;
import cl.myccontadores.cobros.entity.Comprobante;
import cl.myccontadores.cobros.repository.ComprobanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComprobanteServiceImpl implements ComprobanteService {

    @Autowired
    private ComprobanteRepository comprobanteRepository;
    @Autowired
    private FileStorageService fileStorageService;

    @Override
    public ComprobanteDTO crearComprobante(Comprobante comprobante) {
        Comprobante comprobante1 = comprobanteRepository.save(comprobante);
        return new ComprobanteDTO(comprobante1);
    }

    @Override
    public ComprobanteDTO obtenerComprobantePorId(Long id) {
        return comprobanteRepository.findById(id).map(ComprobanteDTO::new).orElseThrow(() -> new RuntimeException("No se encontro el comprobante con id " + id));
    }

    @Override
    public void borrarComprobante(Long id) {
        comprobanteRepository.deleteById(id);
    }
}
