package cl.myccontadores.cobros.service;

import cl.myccontadores.cobros.entity.Item;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ItemService {
    Item crearItemConComprobante(Item item, MultipartFile file) throws IOException;
}
