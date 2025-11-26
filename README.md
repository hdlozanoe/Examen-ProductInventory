# 📄 Examen - Backend
Este proyecto propone una solución compuesta por dos microservicios independientes que interactúan entre sí, utilizando **JSON:API** como estándar para la comunicación.  
El objetivo es garantizar una arquitectura modular, escalable y fácilmente mantenible, donde cada microservicio pueda evolucionar de manera autónoma sin perder la capacidad de integrarse con el resto del sistema.

### 📌 Decisiones tomadas para la solución

- **Microservicios independientes**: Escalabilidad y despliegue separado de *Products* e *Inventory*.  
- **PostgreSQL para persistencia**: Base relacional confiable y soportada por Spring Data JPA.  
- **WebClient con retry y timeout**: Comunicación robusta entre servicios, evitando bloqueos si *Product Service* no responde.  
- **Docker + Docker Compose**: Facilita la ejecución local y pruebas de integración con todos los servicios.  
- **SpringDoc OpenAPI**: Generación automática de documentación de endpoints.  
- **Manejo global de excepciones**: Centraliza respuestas de error en formato **JSON:API**.  
- **Lombok en DTOs y entidades**: Reducción de código repetitivo, mayor legibilidad y mantenibilidad.  
- **Jakarta Validation**: Validación declarativa de datos de entrada, garantizando consistencia en las reglas de negocio.

## 🚀 Instalación y Ejecución

### 📋 Requisitos previos
- Docker & Docker Compose  
- Java 17+  
- Maven 3.8+  
- PostgreSQL *(opcional si se usa Docker Compose)*  
- Postman *(opcional para pruebas manuales)*  

---

### Clonar proyecto
```bash
git clone https://github.com/hdlozanoe/Examen-ProductInventory.git
cd Examen-ProductInventory
```
#### Construcción con Docker Compose

Para levantar todos los servicios del proyecto:

```bash
docker-compose up --build
```
Esto iniciará los siguientes servicios:

- Postgres → puerto 5433

- product-service → puerto 8080

- inventory-service → puerto 8081
  
### Variables de entorno

Definidas en `docker-compose.yml`:

- `PRODUCT_SERVICE_URL` → URL interna de **Product Service** para que **Inventory** pueda comunicarse.  
- `INTERNAL_API_KEY` → Clave interna usada para validar requests entre microservicios.  
- `SPRING_DATASOURCE_*` → Configuración de conexión a la base de datos (usuario, contraseña, host, puerto, nombre de la base).  

## 🔗 Endpoints principales

### 📦 Product Service
| Método | Endpoint           | Descripción                  |
|--------|--------------------|------------------------------|
| POST   | `/products`        | Crear producto               |
| GET    | `/products/{id}`   | Obtener producto por ID       |
| GET    | `/products`        | Listar todos los productos    |
| PUT    | `/products/{id}`   | Actualizar producto           |
| DELETE | `/products/{id}`   | Eliminar producto             |

---

### 📦 Inventory Service
| Método | Endpoint                                | Descripción                        |
|--------|-----------------------------------------|------------------------------------|
| POST   | `/inventory`                            | Crear o actualizar inventario      |
| GET    | `/inventory/{productId}`                | Obtener inventario de producto     |
| PUT    | `/inventory/{productId}/decrease/{amount}` | Disminuir inventario de producto |

## 🧪 Pruebas Unitarias e Integración

### Ejecutar pruebas

Documentación:  
Este comando limpia la construcción previa y ejecuta todas las pruebas unitarias e integración definidas en el proyecto.

Comando para copiar y pegar:
```bash
mvn clean test
```

### 5.2. Pruebas cubiertas

- **Creación, actualización y consulta de productos**  
- **Creación y actualización de inventario**  
- **Decremento de stock** con validación de cantidad suficiente  
- **Comunicación simulada** entre *Inventory Service* y *Product Service*  
- **Manejo de errores**:  
  - `ProductNotFoundException`  
  - `InventoryNotFoundException`  
  - `NotEnoughStockException`  
- **Integración mínima de endpoints**:  
  - `POST /products`  
  - `POST /inventory`  
  - `PUT /inventory/{id}/decrease/{amount}`

  ## 🧪 7. Ejemplo de flujo de prueba (Postman)

### Crear producto
**Request**
```http
POST http://localhost:8080/products
Content-Type: application/json
X-INTERNAL-API-KEY: PRODUCT_123_SECURE_KEY
```
**Body**
```json
{
  "data": {
    "type": "product",
    "attributes": {
      "name": "Laptop",
      "price": 1500.00
    }
  }
}
```
### Crear producto
**Request**
```http
POST http://localhost:8081/inventory
Content-Type: application/json
X-INTERNAL-API-KEY: PRODUCT_123_SECURE_KEY
```
**Body**
```json
{
  "data": {
    "type": "inventory",
    "id": "1",
    "attributes": {
      "quantity": 10
    }
  }
}
```
### Disminuir inventario
Disminuye la cantidad disponible de un producto en inventario.

- **Path Parameters**
  - `productId` → ID del producto (en este ejemplo, `1`)
  - `amount` → Cantidad a disminuir (en este ejemplo, `3`)

- **Ejemplo de request**
```bash
PUT http://localhost:8081/inventory/1/decrease/3
X-INTERNAL-API-KEY: PRODUCT_123_SECURE_KEY
```
**Respuesta exitosa**
```json
{
  "data": {
    "type": "inventory",
    "id": "1",
    "attributes": {
      "quantity": 7
    },
    "links": {
      "self": "/inventory/1",
      "related": null
    }
  },
  "meta": {
    "timestamp": 1700000000000
  }
}

```
### Consultar inventario
Obtiene la información de inventario de un producto específico.
- **Ejemplo de request**
```bash
GET http://localhost:8081/inventory/1
X-INTERNAL-API-KEY: PRODUCT_123_SECURE_KEY
```
- **Path Parameters**
  - `productId` → ID del producto a consultar
**Respuesta exitosa**
```json
{
  "data": {
    "type": "inventory",
    "id": "1",
    "attributes": {
      "quantity": 7
    },
    "links": {
      "self": "/inventory/1",
      "related": null
    }
  },
  "meta": {
    "timestamp": 1700000000000
  }
}


```
## Manejo de errores
### Producto inexistente
Cuando se intenta disminuir o consultar un inventario de un producto que no existe, se retorna:
```json
{
  "errors": [
    {
      "status": "404",
      "title": "Product Not Found",
      "detail": "Producto con id 999 no encontrado"
    }
  ]
}
```
### Inventario insuficiente
Cuando se intenta disminuir una cantidad mayor a la disponible, se retorna:
```json
{
  "errors": [
    {
      "status": "400",
      "title": "Not Enough Stock",
      "detail": "No hay suficiente inventario para el producto con id 1"
    }
  ]
}
```
