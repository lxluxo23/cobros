package cl.myccontadores.cobros.service;

import cl.myccontadores.cobros.entity.Comprobante;
import cl.myccontadores.cobros.entity.FileDB;
import cl.myccontadores.cobros.entity.Item;
import cl.myccontadores.cobros.enums.TipoComprobante;
import cl.myccontadores.cobros.repository.ItemRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Log4j2
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ComprobanteService comprobanteService;

    @Autowired
    private FileStorageService fileStorageService;

    @Override
    public Item crearItemConComprobante(Item item, MultipartFile file) throws IOException {
        FileDB fileDB = fileStorageService.store(file);
        Comprobante comprobante = Comprobante.builder()
                .tipo(TipoComprobante.GASTO)
                .fecha(LocalDateTime.now())
                .file(fileDB)
                .build();
        comprobante =  comprobanteService.crearComprobante(comprobante);
        item.setComprobante(comprobante);
        return itemRepository.save(item);
    }
}
