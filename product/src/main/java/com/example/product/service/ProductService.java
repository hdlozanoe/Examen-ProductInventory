package com.example.product.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.product.dto.ProductJsonApiListResponse;
import com.example.product.dto.ProductJsonApiResponse;
import com.example.product.error.ProductNotFoundException;
import com.example.product.model.Product;
import com.example.product.repository.ProductRepository;

/*
    Servicio para gestionar operaciones relacionadas con productos.
    El servicio contiene la logica de negocio para crear, obtener, actualizar y eliminar productos,
    asi como para convertir entidades de productos a respuestas en formato JSON API.
 */

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /*
        Convierte una entidad de producto a un objeto ProductData para la respuesta JSON API.
        La estrucutura por ejemplo es:
        {
            "id": "1",
            "type": "product",
            "attributes": {
                "name": "Nombre del producto",
                "price": 100.0
            },
            "links": {
                "self": "/products/1"
            }
        }
    */
    private ProductJsonApiResponse.ProductData toData(Product product) {
        ProductJsonApiResponse.ProductData data = new ProductJsonApiResponse.ProductData();
        data.setId(String.valueOf(product.getId()));

        ProductJsonApiResponse.Attributes attributes = new ProductJsonApiResponse.Attributes();
        attributes.setName(product.getName());
        attributes.setPrice(product.getPrice());
        data.setAttributes(attributes);
        
        data.setLinks(
            new ProductJsonApiResponse.Links(
                "/products/" + product.getId(), null
            )
        );

        return data;
    }

    /*
        Convierte una entidad de producto a una respuesta JSON API completa.
        La estructura por ejemplo es:
        {
            "data": {
                "id": "1",
                "type": "product",
                "attributes": {
                    "name": "Nombre del producto",
                    "price": 100.0
                },
                "links": {
                    "self": "/products/1"
                }
            },
            "meta": {
                "timestamp": 1627847265123
            }
        }
    */
    public ProductJsonApiResponse toJsonApiResponse(Product product) {
        ProductJsonApiResponse response = new ProductJsonApiResponse();
        response.setData(toData(product));
        response.setMeta(
            new ProductJsonApiResponse.Meta(System.currentTimeMillis())
        );
        return response;
    }

    /*
        Convierte una lista de entidades de productos a una respuesta JSON API de lista.
        La estructura por ejemplo es:
        {
            "data": [
                {
                    "id": "1",
                    "type": "product",
                    "attributes": {
                        "name": "Nombre del producto 1",
                        "price": 100.0
                    },
                    "links": {
                        "self": "/products/1"
                    }
                },
                {
                    "id": "2",
                    "type": "product",
                    "attributes": {
                        "name": "Nombre del producto 2",
                        "price": 200.0
                    },
                    "links": {
                        "self": "/products/2"
                    }
                }
            ]
        }
    */
    public ProductJsonApiListResponse toJsonApiListResponse(List<Product> products){
        ProductJsonApiListResponse response = new ProductJsonApiListResponse();
        response.setData(
            products.stream()
                .map(this::toData)
                .toList()
        );
        return response;
    }

    // Operaciones CRUD

    // Crea un nuevo producto
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
    
    // Obtiene un producto por su ID
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(
            () -> new ProductNotFoundException(id)
        );
    }

    // Lista todos los productos
    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    // Actualiza un producto existente
    public Product updateProduct(Long id, Product updatedProduct) {
        Product existing = getProduct(id);
        existing.setName(updatedProduct.getName());
        existing.setPrice(updatedProduct.getPrice());
        return productRepository.save(existing);
    }

    // Elimina un producto por su ID
    public void deleteProduct(Long id) {
        getProduct(id);
        productRepository.deleteById(id);
    }

}
