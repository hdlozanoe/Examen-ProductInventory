package inventory.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import com.example.inventory.error.InventoryNotFoundException;
import com.example.inventory.error.NotEnoughStockException;
import com.example.inventory.model.Inventory;
import com.example.inventory.repository.InventoryRepository;
import com.example.inventory.service.InventoryService;

public class InventoryServiceTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private WebClient productWebClient;

    @InjectMocks
    private InventoryService inventoryService;

    private Inventory inventory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        inventory = new Inventory(1L, 10);
    }

    @Test
    void testCreateOrUpdateInventory() {
        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(inventory));
        when(inventoryRepository.save(any())).thenReturn(inventory);

        Inventory result = inventoryService.createOrUpdateInventory(1L, 20);

        assertEquals(20, result.getQuantity());
    }

    @Test
    void testDecreaseQuantity() {
        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(inventory));
        when(inventoryRepository.save(any())).thenReturn(inventory);

        Inventory result = inventoryService.decreaseQuantity(1L, 5);

        assertEquals(5, result.getQuantity());
    }

    @Test
    void testDecreaseQuantityNotEnoughStock() {
        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(inventory));
        assertThrows(NotEnoughStockException.class, () -> inventoryService.decreaseQuantity(1L, 20));
    }

    @Test
    void testGetInventoryByProductIdNotFound() {
        when(inventoryRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(InventoryNotFoundException.class, () -> inventoryService.getInventoryByProductId(2L));
    }
    
}
