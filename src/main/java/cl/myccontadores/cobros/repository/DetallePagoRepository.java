package cl.myccontadores.cobros.repository;

import cl.myccontadores.cobros.entity.DetallePago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DetallePagoRepository extends JpaRepository<DetallePago, Long> {

    List<DetallePago> findByPagoId(Long pagoId);
}
