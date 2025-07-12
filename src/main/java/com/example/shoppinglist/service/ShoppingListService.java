package com.example.shoppinglist.service;

import com.example.shoppinglist.ShoppingItemRepository;
import com.example.shoppinglist.item.ShoppingItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;



@Service
public class ShoppingListService {
    private final ShoppingItemRepository repository;

    @Autowired
    public ShoppingListService(ShoppingItemRepository repository) {
        this.repository = repository;
    }

    public List<ShoppingItem> getAllItems() {
        return repository.findAll();
    }

    public ShoppingItem addItem(ShoppingItem item) {
        return repository.save(item);
    }

    public void deleteItem(Long id) {
        repository.deleteById(id);
    }

    public ShoppingItem markAsPurchased(Long id) {
        ShoppingItem item = repository.findById(id).orElseThrow();
        item.setPurchased(true);
        return repository.save(item);
    }
}
