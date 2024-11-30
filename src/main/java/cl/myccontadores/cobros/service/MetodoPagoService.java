package cl.myccontadores.cobros.service;

import cl.myccontadores.cobros.entity.MetodoPago;

import java.util.List;

public interface MetodoPagoService {

    List<MetodoPago> findAll();
}
