package tn.iteam.web;


import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.iteam.entities.Item;
import tn.iteam.services.ItemService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api")
public class ItemController {

    private final ItemService itemService;
    static String PATH_DIR = "C:\\Users\\yassi\\Desktop\\projet trademarket\\swap-market-services\\ms-item\\ItemPhoto";

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }
    @PostMapping("/items")
    public Item saveItem(@RequestPart("item")  Item item,
                         @RequestPart(required = false) MultipartFile img) throws IOException {
            var profilePict= img.getOriginalFilename().
                    substring(0,img.getOriginalFilename().lastIndexOf('.'))+
                    System.currentTimeMillis()+'.'+img.getOriginalFilename().
                    substring(img.getOriginalFilename().lastIndexOf('.')+1);
            Files.copy(img.getInputStream(), Paths.get(PATH_DIR+ File.separator+profilePict)
            );
            item.setImagePath(profilePict);
            return itemService.saveItem(item);
        }

    @GetMapping("/items/{id}")
    public Item findItemById(@PathVariable Long id){
        return itemService.findItemById(id);
    }

    @GetMapping("/items/image/{fileName}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName) throws IOException {
        Resource resource = new FileSystemResource(PATH_DIR+"ItemPhoto//" + File.separator + fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .contentLength(resource.contentLength())
                .body(resource);
    }
}
