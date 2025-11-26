package com.example.product.controller;

import java.net.URI;
import java.util.List;
import com.example.product.dto.ProductJsonApiListResponse;
import com.example.product.dto.ProductJsonApiRequest;
import com.example.product.dto.ProductJsonApiResponse;
import com.example.product.model.Product;
import com.example.product.service.ProductService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@Tag(
    name = "Product Controller", 
    description = "Operaciones CRUD para productos"
)
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(
        summary = "Crear un nuevo producto",
        description = "Crea un nuevo producto en el sistema.",
        responses = {
                @ApiResponse(
                    responseCode = "201",
                    description = "Producto creado exitosamente",
                    content = @Content(
                        schema = @Schema(implementation = ProductJsonApiResponse.class)
                    )
                ),
                @ApiResponse(
                    responseCode = "400",
                    description = "Solicitud inválida",
                    content = @Content(
                        schema = @Schema(ref = "JsonApiErrorResponse")
                )
            )
        }
    )
    @PostMapping
    public ResponseEntity<ProductJsonApiResponse> createProduct
    (@Valid @RequestBody ProductJsonApiRequest productJsonApiRequest) {
        Product product = toEntity(productJsonApiRequest);
        Product saved = productService.createProduct(product);
        return ResponseEntity
            .created(URI.create("/products/" + saved.getId()))
            .body(productService.toJsonApiResponse(saved));
    }

    @Operation(
        summary = "Obtener un producto por ID",
        description = "Recupera un producto específico utilizando su ID.",
        responses = {
                @ApiResponse(
                    responseCode = "200",
                    description = "Producto recuperado exitosamente",
                    content = @Content(
                        schema = @Schema(implementation = ProductJsonApiResponse.class)
                    )
                ),
                @ApiResponse(
                    responseCode = "404",
                    description = "Producto no encontrado",
                    content = @Content(
                        schema = @Schema(ref = "JsonApiErrorResponse")
                )
            )
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ProductJsonApiResponse> getProduct(@PathVariable Long id) {
        Product product = productService.getProduct(id);
        return ResponseEntity.ok(productService.toJsonApiResponse(product));
    }

    @Operation(
        summary = "Listar todos los productos",
        description = "Recupera una lista de todos los productos disponibles.",
        responses = {
                @ApiResponse(
                    responseCode = "200",
                    description = "Lista de productos recuperada exitosamente",
                    content = @Content(
                        schema = @Schema(implementation = ProductJsonApiListResponse.class)
                    )
                )
        }
    )
    @GetMapping
    public ResponseEntity<ProductJsonApiListResponse> listProducts() {
        List<Product> products = productService.listProducts();
        return ResponseEntity.ok(productService.toJsonApiListResponse(products));
    }

    @Operation(
        summary = "Actualizar un producto existente",
        description = "Actualiza los detalles de un producto específico utilizando su ID.",
        responses = {
                @ApiResponse(
                    responseCode = "200",
                    description = "Producto actualizado exitosamente",
                    content = @Content(
                        schema = @Schema(implementation = ProductJsonApiResponse.class)
                    )
                ),
                @ApiResponse(
                    responseCode = "400",
                    description = "Solicitud inválida",
                    content = @Content(
                        schema = @Schema(ref = "JsonApiErrorResponse")
                )
            ),
                @ApiResponse(
                    responseCode = "404",
                    description = "Producto no encontrado",
                    content = @Content(
                        schema = @Schema(ref = "JsonApiErrorResponse")
                )
            )
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<ProductJsonApiResponse> updateProduct
    (@PathVariable Long id, @Valid @RequestBody ProductJsonApiRequest productJsonApiRequest) {
        Product updated = productService.updateProduct(id, toEntity(productJsonApiRequest));
        return ResponseEntity.ok(productService.toJsonApiResponse(updated));
    }

    @Operation(
        summary = "Eliminar un producto",
        description = "Elimina un producto específico utilizando su ID.",
        responses = {
                @ApiResponse(
                    responseCode = "204",
                    description = "Producto eliminado exitosamente"
                ),
                @ApiResponse(
                    responseCode = "404",
                    description = "Producto no encontrado",
                    content = @Content(
                        schema = @Schema(ref = "JsonApiErrorResponse")
                )
            )
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    private Product toEntity(ProductJsonApiRequest request) {
        Product product = new Product();
        product.setName(request.getData().getAttributes().getName());
        product.setPrice(request.getData().getAttributes().getPrice());
        return product;
    }

}
