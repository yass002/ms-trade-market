package tn.iteam.services;


import org.springframework.stereotype.Service;
import tn.iteam.entities.Item;
import tn.iteam.enums.Status;
import tn.iteam.exceptions.ItemNotFound;
import tn.iteam.repositories.ItemRepository;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
    public List<Item> findAllItems(){
        return itemRepository.findAll();
    }
    public Item findItemById(Long id){
        return itemRepository.findById(id).orElseThrow(
                () ->  new ItemNotFound("Item not found!!")
        );
    }

    public Item saveItem(Item item){
        item.setStatus(Status.AVAILABLE);

        return itemRepository.save(item);
    }
    public void deleteItem(Long id){
        itemRepository.deleteById(id);
    }
}
