package cl.myccontadores.cobros.repository;

import cl.myccontadores.cobros.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findBySaldoPendienteGreaterThan(Integer saldo);
}
