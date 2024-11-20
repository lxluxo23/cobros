package cl.myccontadores.cobros.repository;

import cl.myccontadores.cobros.entity.Cliente;
import cl.myccontadores.cobros.entity.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacturaRepository extends JpaRepository<Factura,Long> {

    List<Factura> findByCliente(Cliente cliente);
    boolean existsByClienteIdAndMesCorrespondienteAndAnio(Long clienteId, Byte mesCorrespondiente, Integer anio);
}
