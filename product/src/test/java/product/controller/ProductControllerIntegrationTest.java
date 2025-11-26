package product.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.product.model.Product;
import com.example.product.repository.ProductRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @Test
    void testCreateAndGetProduct() {
        Product product = new Product("Monitor", BigDecimal.valueOf(300));
        product = productRepository.save(product); // reasignar para asegurar que ID se genere

        assertNotNull(product.getId(), "El ID del producto no debe ser null después de guardar");

        ResponseEntity<String> response = restTemplate.getForEntity(
                "/products/" + product.getId(), String.class);
                
        assertEquals(HttpStatus.OK, response.getStatusCode(), "El status debe ser 200 OK");
        assertNotNull(response.getBody(), "La respuesta no debe ser null");
        assertTrue(response.getBody().contains("Monitor"), "La respuesta debe contener el nombre del producto");
    }
}
