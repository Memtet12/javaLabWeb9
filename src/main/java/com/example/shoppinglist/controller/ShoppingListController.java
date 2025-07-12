package com.example.shoppinglist.controller;

import com.example.shoppinglist.item.ShoppingItem;
import com.example.shoppinglist.service.ShoppingListService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/api/items")
public class ShoppingListController {
    private final ShoppingListService service;

    public ShoppingListController(ShoppingListService service) {
        this.service = service;
    }

    @GetMapping
    public List<ShoppingItem> getAllItems() {
        return service.getAllItems();
    }

    @PostMapping
    public ShoppingItem addItem(@RequestBody ShoppingItem item) {
        return service.addItem(item);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        service.deleteItem(id);
    }

    @PutMapping("/{id}/purchased")
    public ShoppingItem markAsPurchased(@PathVariable Long id) {
        return service.markAsPurchased(id);
    }
}