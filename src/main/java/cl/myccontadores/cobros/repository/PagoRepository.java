package cl.myccontadores.cobros.repository;

import cl.myccontadores.cobros.entity.Cliente;
import cl.myccontadores.cobros.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource
public interface PagoRepository extends JpaRepository<Pago,Long> {
    List<Pago> findByCliente(Cliente cliente);
}
