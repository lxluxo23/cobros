package cl.myccontadores.cobros.service;

import cl.myccontadores.cobros.entity.MetodoPago;
import cl.myccontadores.cobros.repository.MetodoPagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetodoPagoServiceImpl implements MetodoPagoService {
    @Autowired
    private MetodoPagoRepository metodoPagoRepository;
    @Override
    public List<MetodoPago> findAll() {
       return metodoPagoRepository.findAll();
    }
}
