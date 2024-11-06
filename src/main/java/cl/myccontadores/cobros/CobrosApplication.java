package cl.myccontadores.cobros;

import cl.myccontadores.cobros.entity.*;
import cl.myccontadores.cobros.enums.EstadoFactura;
import cl.myccontadores.cobros.enums.TipoComprobante;
import cl.myccontadores.cobros.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootApplication
public class CobrosApplication {

	public static void main(String[] args) {
		SpringApplication.run(CobrosApplication.class, args);
	}


}
