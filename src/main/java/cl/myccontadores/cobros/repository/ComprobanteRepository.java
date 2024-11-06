package cl.myccontadores.cobros.repository;

import cl.myccontadores.cobros.entity.Comprobante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource
public interface ComprobanteRepository extends JpaRepository<Comprobante,Long> {
}
