package com.example.inventory.service;

import com.example.inventory.dto.InventoryJsonApiResponse;
import com.example.inventory.dto.ProductJsonApiResponse;
import com.example.inventory.error.InventoryNotFoundException;
import com.example.inventory.error.NotEnoughStockException;
import com.example.inventory.error.ProductNotFoundException;
import com.example.inventory.error.ProductServiceUnavailableException;
import com.example.inventory.model.Inventory;
import com.example.inventory.repository.InventoryRepository;
import reactor.util.retry.Retry;
import java.time.Duration;
import java.util.concurrent.TimeoutException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final WebClient productWebClient;

    @Value("${internal.api.key}")
    private String internalApiKey;

    public InventoryService(InventoryRepository inventoryRepository, WebClient productWebClient) {
        this.inventoryRepository = inventoryRepository;
        this.productWebClient = productWebClient;
    }

    public ProductJsonApiResponse getProduct(Long productId) {
        try{
            return productWebClient.get()
            .uri("/{id}", productId)
            .header("X-INTERNAL-API-KEY", internalApiKey)
            .retrieve()
            .bodyToMono(ProductJsonApiResponse.class)
            .timeout(Duration.ofSeconds(4))
            .retryWhen(
                Retry.fixedDelay(2, Duration.ofSeconds(1))
                    .filter(throwable -> throwable instanceof WebClientRequestException ||
                                        throwable instanceof TimeoutException)
            )
            .block();
        }catch(WebClientResponseException err){
            if(err.getStatusCode().value() == 404){
                throw new ProductNotFoundException(productId);
            }
            throw new ProductServiceUnavailableException(
                "Error HTTP al comunicarse con el servicio de productos: " + err.getStatusCode().value()
            );
        }catch(WebClientRequestException err){
            throw new ProductServiceUnavailableException("El servicio de productos no está disponible en este momento.");
        }catch(Exception e){
            throw new ProductServiceUnavailableException("Error inesperado al comunicarse con el servicio de productos.");   
        }
    }

    public InventoryJsonApiResponse toJsonApiResponse(Inventory inventory) {
        InventoryJsonApiResponse.InventoryData inventoryData =
        new InventoryJsonApiResponse.InventoryData(
            "inventory",
            String.valueOf(inventory.getProductId()),
            new InventoryJsonApiResponse.Attributes(inventory.getQuantity()),
            new InventoryJsonApiResponse.Links(
                "/inventory/" + inventory.getProductId(),
                "/products/" + inventory.getProductId()
            )
        );
        InventoryJsonApiResponse response = new InventoryJsonApiResponse();
        response.setData(inventoryData);
        response.setMeta(
            new InventoryJsonApiResponse.Meta(System.currentTimeMillis())
        );
        return response;
    }

    public Inventory getInventoryByProductId(Long productId) {
        return inventoryRepository.findById(productId)
            .orElseThrow(() -> new InventoryNotFoundException(productId));
    }

    public Inventory createOrUpdateInventory(Long productId, int quantity) {
        getProduct(productId);
        Inventory inventory = inventoryRepository.findById(productId)
            .orElse(new Inventory(productId, 0));
        inventory.setQuantity(quantity);
        return inventoryRepository.save(inventory);
    }

    public Inventory decreaseQuantity(Long productId, int amount) {
        getProduct(productId);
        Inventory inventory = getInventoryByProductId(productId);
        if (inventory.getQuantity() < amount) {
            throw new NotEnoughStockException(productId);
        }
        inventory.setQuantity(inventory.getQuantity() - amount);
        return inventoryRepository.save(inventory);
    }
    
}
