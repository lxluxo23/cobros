package cl.myccontadores.cobros.repository;

import cl.myccontadores.cobros.entity.Cliente;
import cl.myccontadores.cobros.entity.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource
public interface GastoRepository extends JpaRepository<Gasto,Long> {
    List<Gasto> findByCliente(Cliente cliente);
}
