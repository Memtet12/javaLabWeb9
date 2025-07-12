package com.example.shoppinglist;

import com.example.shoppinglist.item.ShoppingItem;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;



import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class ShoppingListControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShoppingItemRepository repository;


    @Test
    @Sql(scripts = "classpath:cleanup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testAddItemToDatabase() throws Exception {
        assertEquals(0, repository.count());

        mockMvc.perform(post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Хлеб\", \"purchased\":false}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Хлеб"))
                .andExpect(jsonPath("$.purchased").value(false));

        assertEquals(1, repository.count());

        ShoppingItem item = repository.findAll().getFirst();
        assertEquals("Хлеб", item.getName());
        assertFalse(item.isPurchased());
    }

    @Test
    @Sql(scripts = "classpath:cleanup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testMarkAsPurchased() throws Exception {

        ShoppingItem item = new ShoppingItem("Молоко", false);
        item = repository.save(item);

        mockMvc.perform(put("/api/items/{id}/purchased", item.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(item.getId()))
                .andExpect(jsonPath("$.name").value("Молоко"))
                .andExpect(jsonPath("$.purchased").value(true));

        ShoppingItem updatedItem = repository.findById(item.getId()).orElseThrow();
        assertTrue(updatedItem.isPurchased());
    }
}
