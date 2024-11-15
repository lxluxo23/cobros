package cl.myccontadores.cobros.repository;

import cl.myccontadores.cobros.entity.DetalleFactura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleFacturaRepository extends JpaRepository<DetalleFactura,Long> {
}
