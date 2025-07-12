package com.example.shoppinglist;

import com.example.shoppinglist.item.ShoppingItem;
import com.example.shoppinglist.service.ShoppingListService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.runner.RunWith;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class ShoppingListServiceUnitTest {


        @Mock
        private ShoppingItemRepository repository;

        @InjectMocks
        private ShoppingListService service;

        @Test
        public void testMarkAsPurchased_Success() {
            ShoppingItem item = new ShoppingItem("Хлеб", false);
            item.setId(1L);

            when(repository.findById(1L)).thenReturn(Optional.of(item));
            when(repository.save(any(ShoppingItem.class))).thenAnswer(invocation -> invocation.getArgument(0));

            ShoppingItem result = service.markAsPurchased(1L);

            assertTrue(result.isPurchased());
            assertEquals("Хлеб", result.getName());

            verify(repository).findById(1L);
            verify(repository).save(item);
        }

    @Test(expected = RuntimeException.class)
    public void testMarkAsPurchased_ItemNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        service.markAsPurchased(999L);

        verify(repository, never()).save(any());
    }


}
