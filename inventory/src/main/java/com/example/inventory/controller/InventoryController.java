package com.example.inventory.controller;

import com.example.inventory.dto.InventoryJsonApiRequest;
import com.example.inventory.dto.InventoryJsonApiResponse;
import com.example.inventory.error.JsonApiErrorResponse;
import com.example.inventory.model.Inventory;
import com.example.inventory.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @Operation(
        summary = "Obtener inventario por ID de producto",
        description = "Recupera el inventario asociado a un ID de producto específico."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", description = "Inventario recuperado exitosamente",
            content = @Content(schema = @Schema(implementation = InventoryJsonApiResponse.class))
        ),
        @ApiResponse(
            responseCode = "404", description = "Inventario no encontrado",
            content = @Content(schema = @Schema(implementation = JsonApiErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "500", description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = JsonApiErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "503", description = "Servicio de productos no disponible",
            content = @Content(schema = @Schema(implementation = JsonApiErrorResponse.class))
        )
    })
    @GetMapping("/{productId}")
    public ResponseEntity<InventoryJsonApiResponse> getInventory(
        @Parameter(
            description = "ID del producto",
            required = true,
            example = "1"
        )
        @PathVariable Long productId
    ) {
        Inventory inventory = inventoryService.getInventoryByProductId(productId);
        return ResponseEntity.ok(inventoryService.toJsonApiResponse(inventory));
    }

    @Operation(
        summary = "Crear o actualizar inventario",
        description = "Crea un nuevo inventario o actualiza uno existente para un ID de producto específico."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", description = "Inventario creado o actualizado exitosamente",
            content = @Content(schema = @Schema(implementation = InventoryJsonApiResponse.class))
        ),
        @ApiResponse(
            responseCode = "400", description = "Solicitud inválida",
            content = @Content(schema = @Schema(implementation = JsonApiErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "404", description = "Producto no encontrado",
            content = @Content(schema = @Schema(implementation = JsonApiErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "500", description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = JsonApiErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "503", description = "Servicio de productos no disponible",
            content = @Content(schema = @Schema(implementation = JsonApiErrorResponse.class))
        )
    })
    @PostMapping
    public ResponseEntity<InventoryJsonApiResponse> createOrUpdate(
        @RequestBody(
            description = "Datos del inventario para crear o actualizar",
            required = true,
            content = @Content(schema = @Schema(implementation = InventoryJsonApiRequest.class))
        ) @org.springframework.web.bind.annotation.RequestBody InventoryJsonApiRequest inventoryRequest
    ) {
        Long productId = Long.valueOf(inventoryRequest.getData().getId());
        int quantity = inventoryRequest.getData().getAttributes().getQuantity();
        Inventory saved = inventoryService.createOrUpdateInventory(
            productId, 
            quantity
        );
        return ResponseEntity.ok(inventoryService.toJsonApiResponse(saved));
    }

    @Operation(
        summary = "Disminuir cantidad de inventario",
        description = "Disminuye la cantidad de inventario para un ID de producto específico."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", description = "Cantidad de inventario disminuida exitosamente",
            content = @Content(schema = @Schema(implementation = InventoryJsonApiResponse.class))
        ),
        @ApiResponse(
            responseCode = "400", description = "No hay suficiente stock",
            content = @Content(schema = @Schema(implementation = JsonApiErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "404", description = "Producto o inventario no encontrado",
            content = @Content(schema = @Schema(implementation = JsonApiErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "500", description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = JsonApiErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "503", description = "Servicio de productos no disponible",
            content = @Content(schema = @Schema(implementation = JsonApiErrorResponse.class))
        )
    })
    @PutMapping("/{productId}/decrease/{amount}")
    public ResponseEntity<InventoryJsonApiResponse> decreaseQuantity(
        @Parameter(
            description = "ID del producto",
            required = true,
            example = "1"
        )
        @PathVariable Long productId,
        @Parameter(
            description = "Cantidad a disminuir",
            required = true,
            example = "5"
        )
        @PathVariable int amount
    ) {
        Inventory updated = inventoryService.decreaseQuantity(productId, amount);
        return ResponseEntity.ok(inventoryService.toJsonApiResponse(updated));
    }

}