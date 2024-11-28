package cl.myccontadores.cobros.controller;

import cl.myccontadores.cobros.dto.ItemDTO;
import cl.myccontadores.cobros.entity.Item;
import cl.myccontadores.cobros.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})

public class ItemController {
    @Autowired
    private ItemService itemService;

    @PostMapping
    public ResponseEntity<Item> crearItemConComprobante(
            @ModelAttribute ItemDTO itemDTO,
            @RequestParam(value = "file" , required = false) MultipartFile file ) {
        try {
            Item item = new Item();
            item.setNombre(itemDTO.getNombre());
            item.setDescripcion(itemDTO.getDescripcion());
            item.setPrecioUnitario(itemDTO.getPrecioUnitario());
            item.setCategoria(itemDTO.getCategoria());

            Item nuevoItem = itemService.crearItemConComprobante(item, file);
            return ResponseEntity.ok(nuevoItem);
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }
}
