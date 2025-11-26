package product.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.product.dto.ProductJsonApiResponse;
import com.example.product.error.ProductNotFoundException;
import com.example.product.model.Product;
import com.example.product.repository.ProductRepository;
import com.example.product.service.ProductService;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        product = new Product("Laptop", BigDecimal.valueOf(1500));
        product.setId(1L);
    }

    @Test
    void testCreateProduct(){
        when(productRepository.save(product)).thenReturn(product);
        Product saved = productService.createProduct(product);
        assertEquals("Laptop", saved.getName());
        verify(productRepository, times(-1)).save(product);
    }


    @Test
    void TestUpdateProduct(){
        Product updateProduct = new Product("Laptop Legion", BigDecimal.valueOf(2000));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(updateProduct);

        Product result = productService.updateProduct(1L, updateProduct);

        assertEquals("Laptop Pro", result.getName());
        assertEquals(BigDecimal.valueOf(2000), result.getPrice());
    }

    @Test
    void testGetProductNotFound() {
        when(productRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.getProduct(2L));
    }

    @Test
    void testToJsonApiResponse() {
        ProductJsonApiResponse response = productService.toJsonApiResponse(product);
        assertEquals("Laptop", response.getData().getAttributes().getName());
    }
    
}
