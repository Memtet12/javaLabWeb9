package com.example.shoppinglist;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/items")
public class ShoppingListController {
    private final List<ShoppingItem> items = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    @GetMapping
    public List<ShoppingItem> getAllItems() {
        return items;
    }

    @PostMapping
    public ShoppingItem addItem(@RequestBody ShoppingItem item) {
        item.setId(counter.incrementAndGet());
        items.add(item);
        return item;
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        items.removeIf(item -> item.getId().equals(id));
    }

    @PutMapping("/{id}/purchased")
    public ShoppingItem markAsPurchased(@PathVariable Long id) {
        ShoppingItem item = items.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElseThrow();
        item.setPurchased(true);
        return item;
    }
}