package inventory.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.inventory.model.Inventory;
import com.example.inventory.repository.InventoryRepository;

public class InventoryControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Test
    void testCreateAndGetInventory() {
        // Crear inventario manual
        Inventory inventory = new Inventory(1L, 50);
        inventoryRepository.save(inventory);

        ResponseEntity<String> response = restTemplate.getForEntity("/inventory/1", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("\"quantity\":50"));
    }
    
}
