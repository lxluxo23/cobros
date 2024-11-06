package cl.myccontadores.cobros.service;

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
    public Comprobante crearComprobante(Comprobante comprobante) {
       return comprobanteRepository.save(comprobante);
    }

    @Override
    public Comprobante obtenerComprobantePorId(Long id) {
        return  comprobanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el comprobante con id " + id));
    }

    @Override
    public void borrarComprobante(Long id) {
        comprobanteRepository.deleteById(id);
    }
}
